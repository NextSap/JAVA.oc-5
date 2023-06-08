package com.safetynet.alerts.dto;

import com.safetynet.alerts.models.Person;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeopleCoveredByFireStationDto {
    private List<Person> people;
    private int adults;
    private int children;
}
