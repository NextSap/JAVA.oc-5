package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.PersonDto;
import com.safetynet.alerts.entity.MedicalRecordEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/medicalRecord")
public class MedicalRecordController {

    @PostMapping()
    public MedicalRecordEntity createMedicalRecord(@RequestBody MedicalRecordEntity medicalRecord) {
        return null;
    }

    @PutMapping()
    public MedicalRecordEntity updateMedicalRecord(@RequestBody MedicalRecordEntity medicalRecord) {
        return null;
    }

    @DeleteMapping
    public void deleteMedicalRecord(@RequestBody PersonDto personDto) {

    }
}
