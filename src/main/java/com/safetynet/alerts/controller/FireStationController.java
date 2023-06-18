package com.safetynet.alerts.controller;

import com.safetynet.alerts.object.request.FireStationRequest;
import com.safetynet.alerts.object.response.FireStationResponse;
import com.safetynet.alerts.service.FireStationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/firestation")
public class FireStationController {

    private final FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping
    public ResponseEntity<FireStationResponse> createFireStation(@Valid @RequestBody FireStationRequest fireStationRequest) {
        return new ResponseEntity<>(fireStationService.createFireStation(fireStationRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<FireStationResponse> updateFireStation(@Valid @RequestBody FireStationRequest fireStationRequest) {
        return new ResponseEntity<>(fireStationService.updateFireStation(fireStationRequest), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteFireStation(@RequestParam int station) {
        fireStationService.deleteFireStation(station);
    }
}
