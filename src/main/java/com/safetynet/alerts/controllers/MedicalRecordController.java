package com.safetynet.alerts.controllers;

import com.safetynet.alerts.dao.medicalrecord.MedicalRecordDaoImpl;
import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.models.MedicalRecord;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordDaoImpl medicalRecordDaoImpl = new MedicalRecordDaoImpl();

    @PostMapping()
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordDaoImpl.save(medicalRecord);
    }

    @PutMapping()
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordDaoImpl.update(medicalRecord);
    }

    @DeleteMapping
    public void deleteMedicalRecord(@RequestBody PersonDto personDto) {
        medicalRecordDaoImpl.delete(personDto);
    }
}
