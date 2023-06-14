package com.safetynet.alerts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationDto {
    @NotBlank(message = "name:Required") @NotNull(message = "name:Null")
    private String name;
    @NotNull(message = "mlDosage::Null")
    private Long mlDosage;
}
