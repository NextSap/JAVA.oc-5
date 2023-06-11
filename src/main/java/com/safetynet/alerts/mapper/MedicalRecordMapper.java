package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.MedicalRecordDto;
import com.safetynet.alerts.entity.MedicalRecordEntity;

import java.util.stream.Collectors;

public class MedicalRecordMapper {

    private final MedicationMapper medicationMapper = new MedicationMapper();

    public MedicalRecordDto toMedicalRecordDto(MedicalRecordEntity medicalRecordEntity) {
        return MedicalRecordDto.builder()
                .birthdate(medicalRecordEntity.getBirthdate())
                .medications(medicalRecordEntity.getMedications().stream()
                        .map(medicationMapper::toMedicationDto)
                        .collect(Collectors.toList()))
                .allergies(medicalRecordEntity.getAllergies())
                .build();
    }

    public MedicalRecordEntity toMedicalRecordEntity(MedicalRecordDto medicalRecordDto) {
        return MedicalRecordEntity.builder()
                .birthdate(medicalRecordDto.getBirthdate())
                .medications(medicalRecordDto.getMedications().stream()
                        .map(medicationMapper::toMedicationEntity)
                        .collect(Collectors.toList()))
                .allergies(medicalRecordDto.getAllergies())
                .build();
    }
}
