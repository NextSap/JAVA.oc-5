package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HomeDto {
    private AddressDto address;
    private List<PersonWithMedicalsDto> people;
}
