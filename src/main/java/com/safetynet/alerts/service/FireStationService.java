package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.FireStationException;
import com.safetynet.alerts.exception.PersonException;
import com.safetynet.alerts.object.entity.FireStationEntity;
import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.entity.PersonEntity;
import com.safetynet.alerts.mapper.AddressMapper;
import com.safetynet.alerts.mapper.FireStationMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.object.request.FireStationRequest;
import com.safetynet.alerts.object.response.FireResponse;
import com.safetynet.alerts.object.response.FireStationResponse;
import com.safetynet.alerts.object.response.HomeResponse;
import com.safetynet.alerts.object.response.PeopleCoveredByFireStationResponse;
import com.safetynet.alerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    private final PersonMapper personMapper = PersonMapper.getInstance();
    private final FireStationMapper fireStationMapper = FireStationMapper.getInstance();
    private final AddressMapper addressMapper = AddressMapper.getInstance();

    @Autowired
    public FireStationService(FireStationRepository fireStationRepository, PersonService personService, MedicalRecordService medicalRecordService) {
        this.fireStationRepository = fireStationRepository;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    public FireStationEntity getFireStationEntityByStreet(String street) {
        return fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getAddresses().contains(street))
                .findFirst().orElseThrow(() -> new FireStationException.FireStationNotFoundException("FireStation with street `" + street + "` not found"));
    }

    public FireStationEntity getFireStationEntityByStation(int station) {
        return fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getStation() == station)
                .findFirst().orElseThrow(() -> new FireStationException.FireStationNotFoundException("FireStation with station `" + station + "` not found"));
    }

    public Optional<FireStationEntity> getOptionalFireStationEntityByStation(int station) {
        return fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getStation() == station)
                .findFirst();
    }

    public FireStationResponse createFireStation(FireStationRequest fireStationRequest) {
        checkFireStationExists(fireStationRequest.getStation());

        FireStationEntity fireStationEntity = fireStationRepository.save(fireStationMapper.toFireStationEntity(fireStationRequest));
        return fireStationMapper.toFireStationResponse(fireStationEntity);
    }

    public FireStationResponse updateFireStation(FireStationRequest fireStationRequest) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(fireStationRequest.getStation());
        FireStationEntity updatedFireStationEntity = fireStationRepository.save(fireStationMapper.toFireStationEntity(fireStationRequest, fireStationEntity.getId()));

        return fireStationMapper.toFireStationResponse(updatedFireStationEntity);
    }

    public void deleteFireStation(int station) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(station);
        fireStationRepository.delete(fireStationEntity);
    }

    public FireResponse getPeopleAndFireStationFromAddress(String street) {
        FireStationEntity fireStationEntity = getFireStationEntityByStreet(street);
        Map<PersonEntity, MedicalRecordEntity> peopleWithMedicalEntity = getPeopleAndMedicalRecordFromStreet(street);

        return fireStationMapper.toFireResponse(fireStationEntity.getStation(), personMapper.toPersonWithMedicalsResponseList(peopleWithMedicalEntity));
    }

    public List<String> getPhoneFromPeopleCoveredByFireStation(int fireStation) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(fireStation);
        List<PersonEntity> people = personService.getPeopleFromFireStation(fireStationEntity);
        return people.stream().map(PersonEntity::getPhone).toList();
    }

    public PeopleCoveredByFireStationResponse getPeopleCoveredByFireStation(int fireStation) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(fireStation);
        List<PersonEntity> people = personService.getPeopleFromFireStation(fireStationEntity);

        return personMapper.toPeopleCoveredByFireStationResponse(people);
    }

    public List<HomeResponse> getPeopleCoveredByFireStations(Integer[] fireStations) {
        List<HomeResponse> homes = new ArrayList<>();

        for (int fireStation : fireStations) {
            FireStationEntity fireStationEntity = getFireStationEntityByStation(fireStation);

            for (String street : fireStationEntity.getAddresses()) {
                Map<PersonEntity, MedicalRecordEntity> people = getPeopleAndMedicalRecordFromStreet(street);
                Optional<PersonEntity> personEntity = people.keySet().stream().findFirst();

                if (personEntity.isEmpty()) throw new PersonException.PersonNotFoundException("No person found for this address");

                HomeResponse homeResponse = HomeResponse.builder().address(addressMapper.toAddressResponse(personEntity.get().getAddress()))
                        .people(personMapper.toPersonWithMedicalsResponseList(people)).build();
                homes.add(homeResponse);
            }
        }
        return homes;
    }

    public Map<PersonEntity, MedicalRecordEntity> getPeopleAndMedicalRecordFromStreet(String street) {
        List<PersonEntity> people = personService.getPeopleFromStreet(street);
        Map<PersonEntity, MedicalRecordEntity> peopleAndMedicalRecord = new HashMap<>();

        for (PersonEntity person : people)
            peopleAndMedicalRecord.put(person, medicalRecordService.getMedicalRecordEntityByName(person.getFirstName(), person.getLastName()));

        return peopleAndMedicalRecord;
    }

    public Map<PersonEntity, MedicalRecordEntity> getPeopleAndMedicalRecordFromFireStation(FireStationEntity fireStationEntity) {
        List<PersonEntity> people = personService.getPeopleFromFireStation(fireStationEntity);
        Map<PersonEntity, MedicalRecordEntity> peopleAndMedicalRecord = new HashMap<>();

        for (PersonEntity person : people)
            peopleAndMedicalRecord.put(person, medicalRecordService.getMedicalRecordEntityByName(person.getFirstName(), person.getLastName()));

        return peopleAndMedicalRecord;
    }

    private void checkFireStationExists(int station) {
        if (getOptionalFireStationEntityByStation(station).isPresent())
            throw new FireStationException.FireStationAlreadyExistsException("FireStation with station `" + station + "` already exist");
    }
}
