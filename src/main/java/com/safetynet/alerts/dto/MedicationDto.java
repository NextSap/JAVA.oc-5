package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDto {
    private String name;
    private Long mlDosage;
}
