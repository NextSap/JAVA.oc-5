package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimplePersonDto {
    private String firstName;
    private String lastName;
}
