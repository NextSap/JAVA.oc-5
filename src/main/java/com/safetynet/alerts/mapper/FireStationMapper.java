package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.FireStationDto;
import com.safetynet.alerts.entity.FireStationEntity;

public class FireStationMapper {

    public FireStationEntity toFireStationEntity(FireStationDto fireStationDto) {
        return FireStationEntity.builder()
                .addresses(fireStationDto.getAddresses())
                .station(fireStationDto.getStation())
                .build();
    }

    public FireStationDto toFireStationDto(FireStationEntity fireStationEntity) {
        return FireStationDto.builder()
                .addresses(fireStationEntity.getAddresses())
                .station(fireStationEntity.getStation())
                .build();
    }
}
