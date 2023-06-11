package com.safetynet.alerts.dto;

import com.safetynet.alerts.entity.PersonEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChildAlertDto {
    private List<ChildDto> children;
    private List<PersonEntity> familyMembers;
}
