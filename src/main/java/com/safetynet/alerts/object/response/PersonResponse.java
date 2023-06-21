package com.safetynet.alerts.object.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonResponse {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String birthdate;
    private Integer age;
    private AddressResponse address;
    private MedicalRecordResponse medicalRecord;
}
