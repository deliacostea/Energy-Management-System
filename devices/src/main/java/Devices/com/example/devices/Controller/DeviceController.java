package Devices.com.example.devices.Controller;

import Devices.com.example.devices.DTO.DeviceDTO;
import Devices.com.example.devices.Model.Device;
import Devices.com.example.devices.Service.DeviceMessagePublisher;
import Devices.com.example.devices.Service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://frontend.localhost")
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;

    }
    @GetMapping
    public ResponseEntity<List<Device>>getAllDevices() {
        return new ResponseEntity<>(deviceService.getAllDevices(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable long id) {
        Device device = deviceService.getDeviceById(id);
        if (device != null) {
            return new ResponseEntity<>(device,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<String> createDevice(@RequestBody DeviceDTO deviceDTO) {
        if (deviceService.checkUserExists(deviceDTO.getUserId())) {
            deviceService.saveDevice(deviceDTO);
            return ResponseEntity.ok("Dispozitiv creat cu succes.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilizatorul nu există.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable Long id, @RequestBody DeviceDTO deviceDTO) {
        if (deviceService.checkUserExists(deviceDTO.getUserId())) {
            Device updatedDevice = deviceService.updateDevice(id, deviceDTO);
            if (updatedDevice != null) {
                return ResponseEntity.ok("Dispozitiv actualizat cu succes.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dispozitivul nu a fost găsit.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilizatorul nu există.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable Long id) {
        boolean isDeleted = deviceService.deleteDevice(id);
        if (isDeleted) {
            return ResponseEntity.ok("Dispozitiv șters cu succes.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dispozitivul nu a fost găsit.");
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Device>> getDevicesByUserId(@PathVariable Long userId) {
        List<Device> userDevices = deviceService.getDevicesByUserId(userId);
        return ResponseEntity.ok(userDevices);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addDevice(@RequestBody DeviceDTO deviceDTO) {
        if (deviceService.checkUserExists(deviceDTO.getUserId())) {
            deviceService.saveDevice(deviceDTO);
            return ResponseEntity.ok("Dispozitiv creat cu succes.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilizatorul nu există.");
        }
    }
    @PutMapping("/detachUser/{userId}")
    public ResponseEntity<String> detachUserFromDevices(@PathVariable Long userId) {
        deviceService.detachUserFromDevices(userId);
        return ResponseEntity.ok("Dispozitivele au fost actualizate, userId setat la null.");
    }
    @PostMapping("/publishAll")
    public ResponseEntity<String> publishAllDevices() {
        deviceService.publishAllDevices();
        return ResponseEntity.ok("All devices have been published to RabbitMQ.");
    }


}
