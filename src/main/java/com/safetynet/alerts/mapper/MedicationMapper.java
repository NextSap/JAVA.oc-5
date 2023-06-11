package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.MedicationDto;
import com.safetynet.alerts.entity.MedicationEntity;

public class MedicationMapper {

    public MedicationDto toMedicationDto(MedicationEntity medicationEntity) {
        return MedicationDto.builder()
                .name(medicationEntity.getName())
                .mlDosage(medicationEntity.getMlDosage())
                .build();
    }

    public MedicationEntity toMedicationEntity(MedicationDto medicationDto) {
        return MedicationEntity.builder()
                .name(medicationDto.getName())
                .mlDosage(medicationDto.getMlDosage())
                .build();
    }
}
