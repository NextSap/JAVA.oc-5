package com.safetynet.alerts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeopleCoveredByFireStationDto {
    private List<PersonDto> people;
    private int adults;
    private int children;
}
