package com.safetynet.alerts.dao.medicalrecord;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.models.MedicalRecord;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordDao {
    List<MedicalRecord> findAll();
    Optional<MedicalRecord> findByName(PersonDto personDto);
    MedicalRecord save(MedicalRecord medicalRecord);
    MedicalRecord update(MedicalRecord medicalRecord);
    void delete(PersonDto personDto);
}
