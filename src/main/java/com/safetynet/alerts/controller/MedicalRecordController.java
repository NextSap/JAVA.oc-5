package com.safetynet.alerts.controller;

import com.safetynet.alerts.object.request.MedicalRecordRequest;
import com.safetynet.alerts.object.response.MedicalRecordResponse;
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
    public ResponseEntity<MedicalRecordResponse> getMedicalRecord(@Valid @RequestParam String firstName, String lastName) {
        return new ResponseEntity<>(medicalRecordService.getMedicalRecord(firstName, lastName), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(@Valid @RequestBody MedicalRecordRequest medicalRecordRequest) {
        return new ResponseEntity<>(medicalRecordService.createMedicalRecord(medicalRecordRequest), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(@Valid @RequestBody MedicalRecordRequest medicalRecordRequest) {
        return new ResponseEntity<>(medicalRecordService.updateMedicalRecord(medicalRecordRequest), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }
}
