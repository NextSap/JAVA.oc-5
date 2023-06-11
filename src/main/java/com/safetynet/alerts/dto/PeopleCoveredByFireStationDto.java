package com.safetynet.alerts.dto;

import com.safetynet.alerts.entity.PersonEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeopleCoveredByFireStationDto {
    private List<PersonEntity> people;
    private int adults;
    private int children;
}
