package com.safetynet.alerts.dao.firestation;

import com.safetynet.alerts.models.FireStation;

import java.util.List;
import java.util.Optional;

public class FireStationDaoImpl implements FireStationDao {
    @Override
    public List<FireStation> findAll() {
        return null;
    }

    @Override
    public Optional<FireStation> findByStation(int station) {
        return Optional.empty();
    }

    @Override
    public Optional<FireStation> findByAddress(String address) {
        return Optional.empty();
    }

    @Override
    public FireStation save(FireStation fireStation) {
        return null;
    }

    @Override
    public FireStation update(FireStation fireStation) {
        return null;
    }

    @Override
    public void delete(int station) {

    }

    @Override
    public void delete(String address) {

    }
}
