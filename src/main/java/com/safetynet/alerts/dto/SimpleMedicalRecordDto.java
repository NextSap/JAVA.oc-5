package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimpleMedicalRecordDto {
    private List<MedicationDto> medications;
    private List<String> allergies;
}
