package com.safetynet.alerts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.exception.PersonException;
import com.safetynet.alerts.object.entity.*;
import com.safetynet.alerts.object.model.FireStationModel;
import com.safetynet.alerts.object.model.MedicalRecordModel;
import com.safetynet.alerts.object.model.PersonModel;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.util.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class JacksonConfig implements ApplicationRunner {

    private final Logger logger = LogManager.getLogger(JacksonConfig.class);

    private final ObjectMapper objectMapper;

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public JacksonConfig(PersonRepository personRepository, FireStationRepository fireStationRepository, ObjectMapper objectMapper, MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
        this.objectMapper = objectMapper;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    @Value("classpath:data.json")
    private Resource data;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("Loading data from data.json");
        Models models = objectMapper.readValue(data.getInputStream(), Models.class);

        Entities entities = mapPersonAndFireStationEntities(models);

        personRepository.saveAll(entities.getPeople());
        fireStationRepository.saveAll(entities.getFireStations());
        medicalRecordRepository.saveAll(mapMedicalRecords(models.medicalrecords));

        logger.debug("Data loaded and saved to database");
    }

    public Entities mapPersonAndFireStationEntities(Models models) {
        Map<String, PersonEntity> personEntityMap = new HashMap<>();
        Map<Integer, FireStationEntity> fireStationEntityMap = new HashMap<>();
        Map<String, Long> birthdateMap = new HashMap<>();

        for (MedicalRecordModel medicalRecord : models.medicalrecords)
            birthdateMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), DateUtils.getInstance().getDate(medicalRecord.getBirthdate()).getTime());


        for (PersonModel person : models.persons) {
            PersonEntity personEntity = new PersonEntity();

            personEntity.setFirstName(person.getFirstName());
            personEntity.setLastName(person.getLastName());
            personEntity.setPhone(person.getPhone());
            personEntity.setEmail(person.getEmail());
            personEntity.setAddress(AddressEntity.builder().street(person.getAddress()).city(person.getCity()).zip(person.getZip()).build());
            personEntity.setBirthdate(birthdateMap.get(person.getFirstName() + person.getLastName()));

            personEntityMap.put(person.getFirstName() + person.getLastName(), personEntity);
        }

        for (FireStationModel fireStation : models.firestations) {
            if (fireStationEntityMap.containsKey(fireStation.getStation())) {
                FireStationEntity fireStationEntity = fireStationEntityMap.get(fireStation.getStation());
                fireStationEntity.getAddresses().add(fireStation.getAddress());
                continue;
            }

            FireStationEntity fireStationEntity = new FireStationEntity();

            fireStationEntity.setAddresses(new ArrayList<>());
            fireStationEntity.addAddress(fireStation.getAddress());
            fireStationEntity.setStation(fireStation.getStation());

            fireStationEntityMap.put(fireStation.getStation(), fireStationEntity);
        }

        return new Entities(new ArrayList<>(personEntityMap.values()), new ArrayList<>(fireStationEntityMap.values()));
    }

    public List<MedicalRecordEntity> mapMedicalRecords(MedicalRecordModel[] medicalrecords) {
        List<MedicalRecordEntity> medicalRecordEntityList = new ArrayList<>();

        for (MedicalRecordModel medicalrecord : medicalrecords) {
            MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();

            Optional<PersonEntity> personEntity = personRepository.findAll().stream().filter(person -> person.getFirstName().equals(medicalrecord.getFirstName()) && person.getLastName().equals(medicalrecord.getLastName())).findFirst();

            if(personEntity.isEmpty()) throw new PersonException.PersonNotFoundException("Person not found");

            medicalRecordEntity.setPersonId(personEntity.get().getId());

            medicalRecordEntity.setMedications(Arrays.stream(medicalrecord.getMedications()).map(medication -> {
                String[] medicationSplit = medication.split(":");
                return MedicationEntity.builder().name(medicationSplit[0]).mlDosage(Long.parseLong(medicationSplit[1].replace("mg", ""))).build();
            }).collect(Collectors.toList()));

            medicalRecordEntity.setAllergies(Arrays.asList(medicalrecord.getAllergies()));

            medicalRecordEntityList.add(medicalRecordEntity);
        }

        return medicalRecordEntityList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Models {
        private PersonModel[] persons;
        private MedicalRecordModel[] medicalrecords;
        private FireStationModel[] firestations;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Entities {
        private List<PersonEntity> people;
        private List<FireStationEntity> fireStations;
    }
}
