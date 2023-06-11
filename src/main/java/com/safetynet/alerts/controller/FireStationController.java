package com.safetynet.alerts.controller;

import com.safetynet.alerts.entity.FireStationEntity;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/firestation")
public class FireStationController {

    private final FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping
    public FireStationEntity createFireStation(@RequestBody FireStationEntity fireStation) {
        return null;
    }

    @PutMapping
    public FireStationEntity updateFireStation(@RequestBody FireStationEntity fireStation) {
        return null;
    }

    @DeleteMapping
    public void deleteFireStation(@RequestBody String address) {

    }
}
