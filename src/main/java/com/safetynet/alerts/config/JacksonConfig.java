package com.safetynet.alerts.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.entity.*;
import com.safetynet.alerts.model.FireStationModel;
import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.model.PersonModel;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class JacksonConfig implements ApplicationRunner {

    private final ObjectMapper objectMapper;

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;

    public JacksonConfig(PersonRepository personRepository, FireStationRepository fireStationRepository, ObjectMapper objectMapper) {
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
        this.objectMapper = objectMapper;
    }

    @Value("classpath:data.json")
    private Resource data;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Models models = objectMapper.readValue(data.getInputStream(), Models.class);

        Entities entities = mapEntities(models);

        personRepository.saveAll(entities.getPeople());
        fireStationRepository.saveAll(entities.getFireStations());
    }

    public Entities mapEntities(Models models) {
        Map<String, PersonEntity> personEntityMap = new HashMap<>();
        Map<Integer, FireStationEntity> fireStationEntityMap = new HashMap<>();
        Map<String, MedicalRecordEntity> medicalRecordEntityMap = new HashMap<>();
        Map<String, Date> birthdateMap = new HashMap<>();

        for (MedicalRecordModel medicalRecord : models.medicalrecords) {
            MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();

            medicalRecordEntity.setMedications(Arrays.stream(medicalRecord.getMedications()).map(medication -> {
                String[] medicationSplit = medication.split(":");
                return MedicationEntity.builder().name(medicationSplit[0]).mlDosage(Long.parseLong(medicationSplit[1].replace("mg", ""))).build();
            }).collect(Collectors.toList()));

            medicalRecordEntity.setAllergies(Arrays.asList(medicalRecord.getAllergies()));
            medicalRecordEntityMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecordEntity);

            birthdateMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), DateUtils.getDate(medicalRecord.getBirthdate()));
        }

        for (PersonModel person : models.persons) {
            PersonEntity personEntity = new PersonEntity();

            personEntity.setFirstName(person.getFirstName());
            personEntity.setLastName(person.getLastName());
            personEntity.setPhone(person.getPhone());
            personEntity.setEmail(person.getEmail());
            personEntity.setAddress(AddressEntity.builder().street(person.getAddress()).city(person.getCity()).zip(person.getZip()).build());
            personEntity.setBirthdate(birthdateMap.get(person.getFirstName() + person.getLastName()));
            personEntity.setMedicalRecord(medicalRecordEntityMap.get(person.getFirstName() + person.getLastName()));

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
