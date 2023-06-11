package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.FireStationDto;
import com.safetynet.alerts.entity.FireStationEntity;
import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.mapper.FireStationMapper;
import com.safetynet.alerts.mapper.PersonMapper;
import com.safetynet.alerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;

    private final PersonMapper personMapper = new PersonMapper();
    private final FireStationMapper fireStationMapper = new FireStationMapper();

    @Autowired
    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
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
}
