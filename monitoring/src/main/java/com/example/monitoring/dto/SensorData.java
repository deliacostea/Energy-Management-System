package com.example.monitoring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorData {

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("deviceId")
    private long deviceId;

    @JsonProperty("measurementValue")
    private double measurementValue;


    public SensorData() {
    }



    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(double measurementValue) {
        this.measurementValue = measurementValue;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "timestamp=" + timestamp +
                ", deviceId=" + deviceId +
                ", measurementValue=" + measurementValue +
                '}';
    }
}
