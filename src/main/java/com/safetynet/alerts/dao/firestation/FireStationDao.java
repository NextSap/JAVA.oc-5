package com.safetynet.alerts.dao.firestation;

import com.safetynet.alerts.models.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationDao {
    List<FireStation> findAll();
    Optional<FireStation> findByStation(int station);
    Optional<FireStation> findByAddress(String address);
    FireStation save(FireStation fireStation);
    FireStation update(FireStation fireStation);
    void delete(int station);
    void delete(String address);
}
