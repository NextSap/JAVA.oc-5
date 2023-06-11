package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private AddressDto address;
    private MedicalRecordDto medicalRecord;
}
