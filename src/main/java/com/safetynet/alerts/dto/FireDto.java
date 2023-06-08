package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FireDto {
    private int stationNumber;
    private List<PersonWithMedicalsDto> people;
}
