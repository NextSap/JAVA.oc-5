package com.safetynet.alerts.object.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorModel {
    private String field;
    private String cause;
}
