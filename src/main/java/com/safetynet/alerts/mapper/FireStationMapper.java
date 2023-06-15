package com.safetynet.alerts.mapper;

import com.safetynet.alerts.object.entity.FireStationEntity;
import com.safetynet.alerts.object.request.FireStationRequest;
import com.safetynet.alerts.object.response.FireResponse;
import com.safetynet.alerts.object.response.FireStationResponse;
import com.safetynet.alerts.object.response.PersonResponse;

import java.util.List;

public class FireStationMapper {

    private static final FireStationMapper INSTANCE = new FireStationMapper();

    private FireStationMapper() {
    }

    public FireStationEntity toFireStationEntity(FireStationRequest fireStationRequest) {
        return FireStationEntity.builder()
                .addresses(fireStationRequest.getAddresses())
                .station(fireStationRequest.getStation())
                .build();
    }

    public FireStationEntity toFireStationEntity(FireStationRequest fireStationRequest, long id) {
        return FireStationEntity.builder()
                .id(id)
                .addresses(fireStationRequest.getAddresses())
                .station(fireStationRequest.getStation())
                .build();
    }

    public FireStationResponse toFireStationResponse(FireStationEntity fireStationEntity) {
        return FireStationResponse.builder()
                .addresses(fireStationEntity.getAddresses())
                .station(fireStationEntity.getStation())
                .build();
    }

    public FireResponse toFireResponse(int station, List<PersonResponse> people) {
        return FireResponse.builder()
                .station(station)
                .people(people)
                .build();
    }

    public static FireStationMapper getInstance() {
        return INSTANCE;
    }
}
