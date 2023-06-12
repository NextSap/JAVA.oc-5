package com.safetynet.alerts.mapper;

import com.safetynet.alerts.dto.MedicalRecordDto;
import com.safetynet.alerts.dto.SimpleMedicalRecordDto;
import com.safetynet.alerts.entity.MedicalRecordEntity;

import java.util.stream.Collectors;

public class MedicalRecordMapper {

    private static final MedicalRecordMapper INSTANCE = new MedicalRecordMapper();
    private final MedicationMapper medicationMapper = MedicationMapper.getInstance();

    private MedicalRecordMapper() {
    }

    public SimpleMedicalRecordDto toSimpleMedicalRecordDto(MedicalRecordEntity medicalRecordEntity) {
        return SimpleMedicalRecordDto.builder()
                .medications(medicalRecordEntity.getMedications().stream()
                        .map(medicationMapper::toMedicationDto)
                        .collect(Collectors.toList()))
                .allergies(medicalRecordEntity.getAllergies())
                .build();
    }

    public MedicalRecordEntity toMedicalRecordEntity(SimpleMedicalRecordDto simpleMedicalRecordDto) {
        return MedicalRecordEntity.builder()
                .medications(simpleMedicalRecordDto.getMedications().stream()
                        .map(medicationMapper::toMedicationEntity)
                        .collect(Collectors.toList()))
                .allergies(simpleMedicalRecordDto.getAllergies())
                .build();
    }

    public MedicalRecordEntity toMedicalRecordEntity(MedicalRecordDto medicalRecordDto, long personId) {
        return MedicalRecordEntity.builder()
                .personId(personId)
                .medications(medicalRecordDto.getMedications().stream()
                        .map(medicationMapper::toMedicationEntity)
                        .collect(Collectors.toList()))
                .allergies(medicalRecordDto.getAllergies())
                .build();
    }

    public static MedicalRecordMapper getInstance() {
        return INSTANCE;
    }
}
