package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChildDto {
    private String firstName;
    private String lastName;
    private int age;
}
