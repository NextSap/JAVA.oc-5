package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonWithMedicalsDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
