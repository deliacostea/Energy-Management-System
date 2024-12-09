package com.example.monitoring.Repository;


import com.example.monitoring.Model.EnergyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergyDataRepository extends JpaRepository<EnergyData, Long> {
    List<EnergyData> findByDeviceId(long deviceId);
}
