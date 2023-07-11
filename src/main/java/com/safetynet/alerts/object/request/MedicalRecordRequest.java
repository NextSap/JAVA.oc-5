package com.safetynet.alerts.object.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MedicalRecordRequest {
    @NotBlank(message = "firstName:Required") @NotNull(message = "firstName:Null")
    private String firstName;
    @NotBlank(message = "lastName:Required") @NotNull(message = "lastName:Null")
    private String lastName;
    @NotNull(message = "medications:Null")
    private List<MedicationRequest> medications;
    @NotNull(message = "allergies:Null")
    private List<String> allergies;
}
