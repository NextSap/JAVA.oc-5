package com.safetynet.alerts.object.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FireStationModel {
    private String address;
    private int station;
}
