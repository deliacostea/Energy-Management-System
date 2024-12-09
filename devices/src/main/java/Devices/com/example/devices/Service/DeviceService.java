package Devices.com.example.devices.Service;

import Devices.com.example.devices.DTO.DeviceDTO;
import Devices.com.example.devices.DTO.DeviceInfoDTO;
import Devices.com.example.devices.DTO.UserDTO;
import Devices.com.example.devices.Model.Device;
import Devices.com.example.devices.Repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final RestTemplate restTemplate;
    private final DeviceMessagePublisher deviceMessagePublisher;
    @Autowired
    public DeviceService(DeviceRepository deviceRepository, RestTemplate restTemplate, DeviceMessagePublisher deviceMessagePublisher) {
        this.deviceRepository = deviceRepository;
        this.restTemplate = restTemplate;
        this.deviceMessagePublisher = deviceMessagePublisher;
    }
    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }
    public Device getDeviceById(long id){
        return deviceRepository.findById(id).orElse(null);
    }
    public Device createDevice(DeviceDTO deviceDTO){
        Device device = Device.builder()
                .description(deviceDTO.getDescription())
                .address(deviceDTO.getAddress())
                .maxHourlyConsumption(deviceDTO.getMaxHourlyConsumption())
                .userId(deviceDTO.getUserId())  // Ensure userId is set
                .build();
        Device savedDevice = deviceRepository.save(device);

        // Prepare DeviceInfoDTO
        DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO(
                savedDevice.getId(),
                savedDevice.getMaxHourlyConsumption(),
                savedDevice.getUserId()
        );

        // Publish the device information
        deviceMessagePublisher.publishDeviceInfo(deviceInfoDTO);

        return savedDevice;
    }

    public Device updateDevice(Long id, DeviceDTO deviceDTO) {
        Optional<Device> existingDevice = deviceRepository.findById(id);
        if (existingDevice.isPresent()) {
            Device device = existingDevice.get();
            device.setDescription(deviceDTO.getDescription());
            device.setAddress(deviceDTO.getAddress());
            device.setMaxHourlyConsumption(deviceDTO.getMaxHourlyConsumption());
            device.setUserId(deviceDTO.getUserId());
            Device updatedDevice = deviceRepository.save(device);

            // Prepare DeviceInfoDTO
            DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO(
                    updatedDevice.getId(),
                    updatedDevice.getMaxHourlyConsumption(),
                    updatedDevice.getUserId()
            );

            // Publish the device information
            deviceMessagePublisher.publishDeviceInfo(deviceInfoDTO);

            return updatedDevice;
        } else {
            return null;
        }
    }


    public boolean deleteDevice(Long id) {
        Optional<Device> existingDevice = deviceRepository.findById(id);
        if (existingDevice.isPresent()) {
            deviceRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserExists(long userId) {
        String userServiceUrl = "http://user:8081/admin/user/" + userId;
        //String userServiceUrl = "http://localhost:8081/admin/user/" + userId;
        try {
            ResponseEntity<UserDTO> response = restTemplate.getForEntity(userServiceUrl, UserDTO.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            System.err.println("Eroare la conectarea cu serviciul de utilizatori: " + e.getMessage());
            return false;
        }
    }
    public void saveDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setDescription(deviceDTO.getDescription());
        device.setAddress(deviceDTO.getAddress());
        device.setMaxHourlyConsumption(deviceDTO.getMaxHourlyConsumption());
        device.setUserId(deviceDTO.getUserId());
        Device savedDevice = deviceRepository.save(device);

        // Prepare DeviceInfoDTO
        DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO(
                savedDevice.getId(),
                savedDevice.getMaxHourlyConsumption(),
                savedDevice.getUserId()
        );

        // Publish the device information
        deviceMessagePublisher.publishDeviceInfo(deviceInfoDTO);

        //deviceRepository.save(device);
    }
    public List<Device> getDevicesByUserId(Long userId) {
        return deviceRepository.findByUserId(userId);
    }
    public void detachUserFromDevices(Long userId) {
        List<Device> devices = deviceRepository.findByUserId(userId);
        for (Device device : devices) {
            device.setUserId(null);
        }
        deviceRepository.saveAll(devices); 
    }
    public void publishAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        for (Device device : devices) {
            DeviceInfoDTO deviceInfoDTO = new DeviceInfoDTO(
                    device.getId(),
                    device.getMaxHourlyConsumption(),
                    device.getUserId()
            );
            deviceMessagePublisher.publishDeviceInfo(deviceInfoDTO);
        }
        System.out.println("Published all existing devices to RabbitMQ.");
    }
    @EventListener(ApplicationReadyEvent.class)
    public void publishDevicesOnStartup() {
        publishAllDevices();
    }


}
