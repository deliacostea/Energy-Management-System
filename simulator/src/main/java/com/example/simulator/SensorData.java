package com.example.simulator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorData {
    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("deviceId")
    private long deviceId;

    @JsonProperty("measurementValue")
    private double measurementValue;



    public SensorData(long timestamp, long deviceId, double measurementValue) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public double getMeasurementValue() {
        return measurementValue;
    }


    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public void setMeasurementValue(double measurementValue) {
        this.measurementValue = measurementValue;
    }
}
