package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonWithMedicalsAndEmailDTO {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String email;
    private List<String> medications;
    private List<String> allergies;
}
