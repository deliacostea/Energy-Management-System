package com.example.monitoring.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "device_info")
public class DeviceInfo {

    @Id
    private long deviceId;

    private double maxHourlyConsumption;
    private long userId;

    public DeviceInfo() {
    }

    public DeviceInfo(long deviceId, double maxHourlyConsumption, long userId) {
        this.deviceId = deviceId;
        this.maxHourlyConsumption = maxHourlyConsumption;
        this.userId = userId;
    }



    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public double getMaxHourlyConsumption() {
        return maxHourlyConsumption;
    }

    public void setMaxHourlyConsumption(double maxHourlyConsumption) {
        this.maxHourlyConsumption = maxHourlyConsumption;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceId=" + deviceId +
                ", maxHourlyConsumption=" + maxHourlyConsumption +
                ", userId=" + userId +
                '}';
    }
}
