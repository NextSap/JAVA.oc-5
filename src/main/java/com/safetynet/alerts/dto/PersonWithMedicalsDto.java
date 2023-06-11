package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonWithMedicalsDto {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private MedicalRecordDto medicalRecord;
}
