package com.safetynet.alerts.controllers;

import com.safetynet.alerts.dao.firestation.FireStationDaoImpl;
import com.safetynet.alerts.models.FireStation;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/firestation")
public class FireStationController {

    private final FireStationDaoImpl fireStationDaoImpl = new FireStationDaoImpl();

    @PostMapping
    public FireStation createFireStation(@RequestBody FireStation fireStation) {
        return fireStationDaoImpl.save(fireStation);
    }

    @PutMapping
    public FireStation updateFireStation(@RequestBody FireStation fireStation) {
        return fireStationDaoImpl.update(fireStation);
    }

    @DeleteMapping
    public void deleteFireStation(@RequestBody String address) {
        fireStationDaoImpl.delete(address);
    }

    @DeleteMapping
    public void deleteFireStation(@RequestBody int station) {
        fireStationDaoImpl.delete(station);
    }
}
