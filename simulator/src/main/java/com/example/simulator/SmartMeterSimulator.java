package com.example.simulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class SmartMeterSimulator {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    private final long deviceId = 70;


    public SmartMeterSimulator(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }


    public void simulateMeterData() {
        String filePath = "src/main/resources/sensor.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                try {
                    double measurementValue = Double.parseDouble(line);


                    long timestamp = System.currentTimeMillis();


                    SensorData sensorData = new SensorData(timestamp, deviceId, measurementValue);


                    System.out.println("Sending the following data to RabbitMQ: " + sensorData);
                    rabbitTemplate.convertAndSend(exchange, routingKey, sensorData);


                    Thread.sleep(10000); // Pauza de 10 secunde
                } catch (NumberFormatException e) {

                    System.out.println("Invalid data format in line: " + line);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
