package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireDto;
import com.safetynet.alerts.dto.FireStationDto;
import com.safetynet.alerts.dto.PeopleCoveredByFireStationDto;
import com.safetynet.alerts.entity.FireStationEntity;
import com.safetynet.alerts.entity.PersonEntity;
import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.mapper.FireStationMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonService personService;

    private final PersonMapper personMapper = new PersonMapper();
    private final FireStationMapper fireStationMapper = new FireStationMapper();

    @Autowired
    public FireStationService(FireStationRepository fireStationRepository, PersonService personService) {
        this.fireStationRepository = fireStationRepository;
        this.personService = personService;
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
        fireStationRepository.save(fireStationEntity);
        return fireStationDto;
    }

    public void deleteFireStation(int station) {
        FireStationEntity fireStationEntity = getFireStationEntityByStation(station);
        fireStationRepository.delete(fireStationEntity);
    }

    public FireDto getPeopleAndFireStationFromAddress(String street) {
        FireStationEntity fireStationEntity = getFireStationEntityByStreet(street);
        List<PersonEntity> people = personService.getPeopleFromStreet(street);
        return FireDto.builder().station(fireStationEntity.getStation())
                .people(personMapper.toPersonWithMedicalsDtoList(people)).build();
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
}
