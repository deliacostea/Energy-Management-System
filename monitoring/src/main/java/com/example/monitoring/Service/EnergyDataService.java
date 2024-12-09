package com.example.monitoring.Service;

import com.example.monitoring.Model.DeviceInfo;
import com.example.monitoring.Model.EnergyData;
import com.example.monitoring.Repository.DeviceInfoRepository;
import com.example.monitoring.dto.SensorData;

import com.example.monitoring.Repository.EnergyDataRepository;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class EnergyDataService {

    private final EnergyDataRepository energyDataRepository;
    private final DeviceInfoRepository deviceInfoRepository;

    private final Map<Long, List<Double>> deviceMeasurements = new HashMap<>();

    public EnergyDataService(EnergyDataRepository energyDataRepository, DeviceInfoRepository deviceInfoRepository) {
        this.energyDataRepository = energyDataRepository;
        this.deviceInfoRepository = deviceInfoRepository;
    }

    public synchronized void processMeasurement(SensorData sensorData) {
        long deviceId = sensorData.getDeviceId();
        double measurementValue = sensorData.getMeasurementValue();
        System.out.println("measurement value: " + measurementValue);


        List<Double> measurements = deviceMeasurements.computeIfAbsent(deviceId, k -> new ArrayList<>());


        measurements.add(measurementValue);


        if (measurements.size() == 6) {
            double averageConsumption = measurements.stream().mapToDouble(Double::doubleValue).sum();
            System.out.println("average consumption: " + averageConsumption);
            Optional<DeviceInfo> optionalDeviceInfo = deviceInfoRepository.findById(deviceId);
            if (optionalDeviceInfo.isPresent()) {
                DeviceInfo deviceInfo = optionalDeviceInfo.get();
                double maxHourlyConsumption = deviceInfo.getMaxHourlyConsumption();
                System.out.println("maxHourlyConsumption: " + maxHourlyConsumption);



                if (averageConsumption > maxHourlyConsumption) {

                    System.out.println("Alert: Device " + deviceId + " exceeded maximum hourly consumption!");

                }
            } else {
                System.out.println("DeviceInfo not found for deviceId: " + deviceId);
            }
            EnergyData energyData = new EnergyData();
            energyData.setDeviceId(deviceId);
            energyData.setTotalConsumption(averageConsumption);
            energyData.setTimestamp(System.currentTimeMillis());


            energyDataRepository.save(energyData);


            measurements.clear();


        }
    }

}
