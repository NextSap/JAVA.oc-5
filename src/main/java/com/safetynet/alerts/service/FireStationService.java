package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireDto;
import com.safetynet.alerts.dto.FireStationDto;
import com.safetynet.alerts.dto.HomeDto;
import com.safetynet.alerts.dto.PeopleCoveredByFireStationDto;
import com.safetynet.alerts.entity.FireStationEntity;
import com.safetynet.alerts.entity.MedicalRecordEntity;
import com.safetynet.alerts.entity.PersonEntity;
import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.mapper.AddressMapper;
import com.safetynet.alerts.mapper.FireStationMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .findFirst().orElseThrow(() -> new FireStationNotFoundException("FireStation with address " + street + " not found"));
    }

    public FireStationEntity getFireStationEntityByStation(int station) {
        return fireStationRepository.findAll().stream()
                .filter(fireStation -> fireStation.getStation() == station)
                .findFirst().orElseThrow(() -> new FireStationNotFoundException("FireStation with station " + station + " not found"));
    }

    public FireStationDto createFireStation(FireStationDto fireStationDto) {
        FireStationEntity fireStationEntity = fireStationMapper.toFireStationEntity(fireStationDto);
        fireStationRepository.save(fireStationEntity);
        return fireStationDto;
    }

    public FireStationDto updateFireStation(FireStationDto fireStationDto) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(fireStationDto.getStation());
        FireStationEntity updatedFireStationEntity = fireStationRepository.save(fireStationMapper.toFireStationEntity(fireStationDto, fireStationEntity.getId()));

        return fireStationMapper.toFireStationDto(updatedFireStationEntity);
    }

    public void deleteFireStation(int station) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(station);
        fireStationRepository.delete(fireStationEntity);
    }

    public FireDto getPeopleAndFireStationFromAddress(String street) {
        FireStationEntity fireStationEntity = getFireStationEntityByStreet(street);
        Map<PersonEntity, MedicalRecordEntity> peopleWithMedicalEntity = getPeopleAndMedicalRecordFromStreet(street);
        return FireDto.builder().station(fireStationEntity.getStation())
                .people(personMapper.toPersonWithMedicalsDtoList(peopleWithMedicalEntity)).build();
    }

    public List<String> getPhoneFromPeopleCoveredByFireStation(int fireStation) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(fireStation);
        List<PersonEntity> people = personService.getPeopleFromFireStation(fireStationEntity);
        return people.stream().map(PersonEntity::getPhone).toList();
    }

    public PeopleCoveredByFireStationDto getPeopleCoveredByFireStation(int fireStation) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(fireStation);
        List<PersonEntity> people = personService.getPeopleFromFireStation(fireStationEntity);
        return personMapper.toPeopleCoveredByFireStationDto(people);
    }

    public List<HomeDto> getPeopleCoveredByFireStations(Integer[] fireStations) {
        List<HomeDto> homes = new ArrayList<>();

        for (int fireStation : fireStations) {
            FireStationEntity fireStationEntity = getFireStationEntityByStation(fireStation);
            for (String street : fireStationEntity.getAddresses()) {
                Map<PersonEntity, MedicalRecordEntity> people = getPeopleAndMedicalRecordFromStreet(street);
                HomeDto homeDto = HomeDto.builder().address(addressMapper.toAddressDto(people.keySet().stream().findFirst().get().getAddress()))
                        .people(personMapper.toPersonWithMedicalsDtoList(people)).build();
                homes.add(homeDto);
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
}
