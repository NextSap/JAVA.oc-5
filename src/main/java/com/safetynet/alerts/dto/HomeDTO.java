package com.safetynet.alerts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HomeDTO {
    private String address;
    private String city;
    private String zip;
    private int station;
    private List<PersonWithMedicalsDTO> persons;
}
