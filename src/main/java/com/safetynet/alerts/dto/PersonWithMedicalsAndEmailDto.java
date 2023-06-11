package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonWithMedicalsAndEmailDto {
    private String firstName;
    private String lastName;
    private AddressDto address;
    private String email;
    private MedicalRecordDto medicalRecord;
}
