package com.safetynet.alerts.mapper;

import com.safetynet.alerts.object.entity.MedicalRecordEntity;
import com.safetynet.alerts.object.request.MedicalRecordRequest;
import com.safetynet.alerts.object.response.MedicalRecordResponse;

import java.util.stream.Collectors;

public class MedicalRecordMapper {

    private static final MedicalRecordMapper INSTANCE = new MedicalRecordMapper();
    private final MedicationMapper medicationMapper = MedicationMapper.getInstance();

    private MedicalRecordMapper() {
    }

    public MedicalRecordResponse toMedicalRecordResponse(MedicalRecordEntity medicalRecordEntity, String firstName, String lastName) {
        return MedicalRecordResponse.builder()
                .firstName(firstName)
                .lastName(lastName)
                .medications(medicalRecordEntity.getMedications().stream()
                        .map(medicationMapper::toMedicationResponse)
                        .collect(Collectors.toList()))
                .allergies(medicalRecordEntity.getAllergies())
                .build();
    }

    public MedicalRecordEntity toMedicalRecordEntity(MedicalRecordRequest medicalRecordRequest) {
        return MedicalRecordEntity.builder()
                .medications(medicalRecordRequest.getMedications().stream()
                        .map(medicationMapper::toMedicationEntity)
                        .collect(Collectors.toList()))
                .allergies(medicalRecordRequest.getAllergies())
                .build();
    }

    public MedicalRecordEntity toMedicalRecordEntity(MedicalRecordRequest medicalRecordRequest, long personId) {
        return MedicalRecordEntity.builder()
                .personId(personId)
                .medications(medicalRecordRequest.getMedications().stream()
                        .map(medicationMapper::toMedicationEntity)
                        .collect(Collectors.toList()))
                .allergies(medicalRecordRequest.getAllergies())
                .build();
    }

    public MedicalRecordResponse toSimpleMedicalRecordResponse(MedicalRecordEntity medicalRecordEntity) {
        return MedicalRecordResponse.builder()
                .medications(medicalRecordEntity.getMedications().stream()
                        .map(medicationMapper::toMedicationResponse)
                        .collect(Collectors.toList()))
                .allergies(medicalRecordEntity.getAllergies())
                .build();
    }

    public static MedicalRecordMapper getInstance() {
        return INSTANCE;
    }
}
