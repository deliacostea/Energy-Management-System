package com.example.monitoring.Consumer;

import com.example.monitoring.Config.RabbitMQConfig;
import com.example.monitoring.dto.DeviceInfoDTO;
import com.example.monitoring.Model.DeviceInfo;
import com.example.monitoring.Repository.DeviceInfoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeviceInfoConsumer {

    private final DeviceInfoRepository deviceInfoRepository;

    public DeviceInfoConsumer(DeviceInfoRepository deviceInfoRepository) {
        this.deviceInfoRepository = deviceInfoRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.DEVICE_QUEUE)
    public void receiveDeviceInfo(@Payload DeviceInfoDTO deviceInfoDTO) {

        if (deviceInfoDTO.getMaxHourlyConsumption() == 0.0 && deviceInfoDTO.getUserId() == 0) {

            return;
        }

        System.out.println("Received DeviceInfoDTO: deviceId=" + deviceInfoDTO.getDeviceId()
                + ", maxHourlyConsumption=" + deviceInfoDTO.getMaxHourlyConsumption()
                + ", userId=" + deviceInfoDTO.getUserId());


        Optional<DeviceInfo> existingDeviceInfo = deviceInfoRepository.findById(deviceInfoDTO.getDeviceId());

        if (existingDeviceInfo.isPresent()) {

            if (deviceInfoDTO.getMaxHourlyConsumption() > 0 && deviceInfoDTO.getUserId() > 0) {

                DeviceInfo deviceInfo = existingDeviceInfo.get();
                deviceInfo.setMaxHourlyConsumption(deviceInfoDTO.getMaxHourlyConsumption());
                deviceInfo.setUserId(deviceInfoDTO.getUserId());
                deviceInfoRepository.save(deviceInfo);
                System.out.println("Updated device info: " + deviceInfo);
            } else {
                System.out.println("Received invalid update for deviceId=" + deviceInfoDTO.getDeviceId() + ". Skipping update.");
            }
        } else {

            DeviceInfo deviceInfo = new DeviceInfo(
                    deviceInfoDTO.getDeviceId(),
                    deviceInfoDTO.getMaxHourlyConsumption(),
                    deviceInfoDTO.getUserId()
            );

            deviceInfoRepository.save(deviceInfo);

            System.out.println("Received and saved device info: " + deviceInfo);
        }
    }
}
