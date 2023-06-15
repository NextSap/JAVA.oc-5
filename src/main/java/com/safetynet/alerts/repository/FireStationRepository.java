package com.safetynet.alerts.repository;

import com.safetynet.alerts.object.entity.FireStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository<FireStationEntity, Long> {
}
