package com.safetynet.alerts.object.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalRecordModel {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String[] medications;
    private String[] allergies;
}
