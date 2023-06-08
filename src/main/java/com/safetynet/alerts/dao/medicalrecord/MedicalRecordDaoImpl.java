package com.safetynet.alerts.dao.medicalrecord;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.models.MedicalRecord;

import java.util.List;
import java.util.Optional;

public class MedicalRecordDaoImpl implements MedicalRecordDao {
    @Override
    public List<MedicalRecord> findAll() {
        return null;
    }

    @Override
    public Optional<MedicalRecord> findByName(PersonDto personDto) {
        return Optional.empty();
    }

    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) {
        return null;
    }

    @Override
    public MedicalRecord update(MedicalRecord medicalRecord) {
        return null;
    }

    @Override
    public void delete(PersonDto personDto) {

    }
}
