package com.safetynet.alerts.object.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordModel {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String[] medications;
    private String[] allergies;
}
