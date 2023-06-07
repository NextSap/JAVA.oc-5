package com.safetynet.alerts.dto;

import com.safetynet.alerts.models.Person;
import lombok.Data;

import java.util.List;

@Data
public class PeopleInFireStationDTO {
    private List<Person> people;
    private int adults;
    private int children;
}
