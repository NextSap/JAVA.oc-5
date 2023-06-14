package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.MedicalRecordDto;
import com.safetynet.alerts.dto.SimpleMedicalRecordDto;
import com.safetynet.alerts.dto.SimplePersonDto;
import com.safetynet.alerts.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping()
    public ResponseEntity<SimpleMedicalRecordDto> getMedicalRecord(@Valid @RequestBody SimplePersonDto personDto) {
        return new ResponseEntity<>(medicalRecordService.getMedicalRecord(personDto.getFirstName(), personDto.getLastName()), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MedicalRecordDto> createMedicalRecord(@Valid @RequestBody MedicalRecordDto medicalRecord) {
        return new ResponseEntity<>(medicalRecordService.createMedicalRecord(medicalRecord), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<MedicalRecordDto> updateMedicalRecord(@Valid @RequestBody MedicalRecordDto medicalRecord) {
        return new ResponseEntity<>(medicalRecordService.updateMedicalRecord(medicalRecord), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteMedicalRecord(@RequestBody SimplePersonDto personDto) {
        medicalRecordService.deleteMedicalRecord(personDto.getFirstName(), personDto.getLastName());
    }
}
