package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HomeDto {
    private String address;
    private String city;
    private String zip;
    private int station;
    private List<PersonWithMedicalsDto> persons;
}
