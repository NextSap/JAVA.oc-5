package com.safetynet.alerts.mapper;

import com.safetynet.alerts.object.entity.MedicationEntity;
import com.safetynet.alerts.object.request.MedicationRequest;
import com.safetynet.alerts.object.response.MedicationResponse;

public class MedicationMapper {

    private static final MedicationMapper INSTANCE = new MedicationMapper();

    private MedicationMapper() {
    }

    public MedicationResponse toMedicationResponse(MedicationEntity medicationEntity) {
        return MedicationResponse.builder()
                .name(medicationEntity.getName())
                .mlDosage(medicationEntity.getMlDosage())
                .build();
    }

    public MedicationEntity toMedicationEntity(MedicationRequest medicationRequest) {
        return MedicationEntity.builder()
                .name(medicationRequest.getName())
                .mlDosage(medicationRequest.getMlDosage())
                .build();
    }

    public static MedicationMapper getInstance() {
        return INSTANCE;
    }
}
