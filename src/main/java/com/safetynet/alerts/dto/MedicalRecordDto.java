package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class MedicalRecordDto {
    private Date birthdate;
    private List<MedicationDto> medications;
    private List<String> allergies;
}
