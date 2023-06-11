package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FireStationDto {
    private int station;
    private List<String> addresses;
}
