package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonWithMedicalsAndEmailDto {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String email;
    private List<String> medications;
    private List<String> allergies;
}
