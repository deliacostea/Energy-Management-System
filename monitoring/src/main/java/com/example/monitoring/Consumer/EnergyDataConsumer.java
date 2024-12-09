package com.example.monitoring.Consumer;

import com.example.monitoring.Config.RabbitMQConfig;
import com.example.monitoring.dto.SensorData;
import com.example.monitoring.Service.EnergyDataService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EnergyDataConsumer {

    private final EnergyDataService energyDataService;

    public EnergyDataConsumer(EnergyDataService energyDataService) {
        this.energyDataService = energyDataService;
    }

    @RabbitListener(queues = RabbitMQConfig.SENSOR_QUEUE)
    public void consumeMessage(SensorData sensorData) {
        try {

            energyDataService.processMeasurement(sensorData);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
