package com.safetynet.alerts.object.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicationRequest {
    @NotBlank(message = "name:Required") @NotNull(message = "name:Null")
    private String name;
    @NotNull(message = "mlDosage::Null")
    private Long mlDosage;
}
