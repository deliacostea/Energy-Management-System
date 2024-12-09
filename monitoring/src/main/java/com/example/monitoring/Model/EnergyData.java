package com.example.monitoring.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "energy_data")
public class EnergyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long deviceId;
    private long timestamp;
    private Double totalConsumption;


    public EnergyData() {
    }



    public Long getId() {
        return id;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Double totalConsumption) {
        this.totalConsumption = totalConsumption;
    }
}
