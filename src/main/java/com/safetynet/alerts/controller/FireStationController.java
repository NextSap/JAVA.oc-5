package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.FireStationDto;
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
    public ResponseEntity<FireStationDto> createFireStation(@Valid @RequestBody FireStationDto fireStation) {
        return new ResponseEntity<>(fireStationService.createFireStation(fireStation), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<FireStationDto> updateFireStation(@Valid @RequestBody FireStationDto fireStation) {
        return new ResponseEntity<>(fireStationService.updateFireStation(fireStation), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteFireStation(@Valid @RequestBody int station) {
        fireStationService.deleteFireStation(station);
    }
}
