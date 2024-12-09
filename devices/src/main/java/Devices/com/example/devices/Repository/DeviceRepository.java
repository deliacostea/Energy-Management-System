package Devices.com.example.devices.Repository;

import Devices.com.example.devices.Model.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Long> {
    List<Device> findAll();

    List<Device> findByUserId(Long userId);
}
