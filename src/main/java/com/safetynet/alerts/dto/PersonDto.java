package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PersonDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Date birthdate;
    private AddressDto address;
}
