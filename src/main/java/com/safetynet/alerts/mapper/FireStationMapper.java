package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.FireStationDto;
import com.safetynet.alerts.entity.FireStationEntity;

public class FireStationMapper {

    private static final FireStationMapper INSTANCE = new FireStationMapper();

    private FireStationMapper() {
    }

    public FireStationEntity toFireStationEntity(FireStationDto fireStationDto) {
        return FireStationEntity.builder()
                .addresses(fireStationDto.getAddresses())
                .station(fireStationDto.getStation())
                .build();
    }

    public FireStationEntity toFireStationEntity(FireStationDto fireStationDto, long id) {
        return FireStationEntity.builder()
                .id(id)
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

    public static FireStationMapper getInstance() {
        return INSTANCE;
    }
}
