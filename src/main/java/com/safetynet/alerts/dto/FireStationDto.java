package com.safetynet.alerts.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FireStationDto {
    @NotNull(message = "station:Null")
    private Integer station;
    @NotNull(message = "addresses:Null")
    private List<String> addresses;
}
