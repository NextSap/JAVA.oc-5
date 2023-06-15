package com.safetynet.alerts.object.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireStationModel {
    private String address;
    private int station;
}
