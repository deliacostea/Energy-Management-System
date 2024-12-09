package Devices.com.example.devices.Service;

import Devices.com.example.devices.Config.RabbitMQConfig;
import Devices.com.example.devices.DTO.DeviceInfoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DeviceMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public DeviceMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishDeviceInfo(DeviceInfoDTO deviceInfoDTO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEVICE_EXCHANGE, routingKey, deviceInfoDTO);
        System.out.println("Published device info to RabbitMQ: " + deviceInfoDTO);
    }
}
