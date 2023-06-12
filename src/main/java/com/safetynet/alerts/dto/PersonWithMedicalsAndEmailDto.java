package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonWithMedicalsAndEmailDto {
    private String firstName;
    private String lastName;
    private AddressDto address;
    private String email;
    private SimpleMedicalRecordDto medicalRecord;
}
