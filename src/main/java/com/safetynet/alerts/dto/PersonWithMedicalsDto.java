package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonWithMedicalsDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
