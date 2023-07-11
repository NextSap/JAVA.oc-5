package com.safetynet.alerts.controller;

import com.safetynet.alerts.object.request.FireStationRequest;
import com.safetynet.alerts.object.response.FireStationResponse;
import com.safetynet.alerts.service.FireStationService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/firestation")
public class FireStationController {

    private final Logger logger = LogManager.getLogger(FireStationController.class);
    private final FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping
    public ResponseEntity<FireStationResponse> createFireStation(@Valid @RequestBody FireStationRequest fireStationRequest) {
        FireStationResponse fireStationResponse = fireStationService.createFireStation(fireStationRequest);
        logger.info("Successful Request POST /firestation");
        return new ResponseEntity<>(fireStationResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<FireStationResponse> updateFireStation(@Valid @RequestBody FireStationRequest fireStationRequest) {
        FireStationResponse fireStationResponse = fireStationService.updateFireStation(fireStationRequest);
        logger.info("Successful Request PUT /firestation");
        return new ResponseEntity<>(fireStationResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteFireStation(@RequestParam int station) {
        fireStationService.deleteFireStation(station);
        logger.info("Successful Request DELETE /firestation?station=" + station);
    }
}
