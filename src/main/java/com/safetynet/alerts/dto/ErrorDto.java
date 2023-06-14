package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private String field;
    private String cause;
}
