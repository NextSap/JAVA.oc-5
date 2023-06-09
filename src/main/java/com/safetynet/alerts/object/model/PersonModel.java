package com.safetynet.alerts.object.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonModel {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
