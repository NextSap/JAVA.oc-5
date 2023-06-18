package com.safetynet.alerts.controller;

import com.safetynet.alerts.object.request.MedicalRecordRequest;
import com.safetynet.alerts.object.response.MedicalRecordResponse;
import com.safetynet.alerts.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/medicalRecord")
public class MedicalRecordController {

    private final Logger logger = LogManager.getLogger(MedicalRecordController.class);
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public ResponseEntity<MedicalRecordResponse> getMedicalRecord(@Valid @RequestParam String firstName, String lastName) {
        MedicalRecordResponse medicalRecordResponse = medicalRecordService.getMedicalRecord(firstName, lastName);
        logger.info("Successful Request GET /medicalRecord?firstName="+firstName+"&lastName="+lastName);
        return new ResponseEntity<>(medicalRecordResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> createMedicalRecord(@Valid @RequestBody MedicalRecordRequest medicalRecordRequest) {
        MedicalRecordResponse medicalRecordResponse = medicalRecordService.createMedicalRecord(medicalRecordRequest);
        logger.info("Successful Request POST /medicalRecord");
        return new ResponseEntity<>(medicalRecordResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MedicalRecordResponse> updateMedicalRecord(@Valid @RequestBody MedicalRecordRequest medicalRecordRequest) {
        MedicalRecordResponse medicalRecordResponse = medicalRecordService.updateMedicalRecord(medicalRecordRequest);
        logger.info("Successful Request PUT /medicalRecord");
        return new ResponseEntity<>(medicalRecordResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        logger.info("Successful Request DELETE /medicalRecord?firstName="+firstName+"&lastName="+lastName);
    }
}
