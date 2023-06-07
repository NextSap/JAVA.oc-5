package com.safetynet.alerts.dto;

import com.safetynet.alerts.models.Person;
import lombok.Data;

import java.util.List;

@Data
public class ChildAlertDTO {
    private List<ChildDTO> children;
    private List<Person> familyMembers;
}
