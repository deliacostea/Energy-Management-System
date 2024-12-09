package Devices.com.example.devices.DTO;

import lombok.Data;

@Data
public class DeviceDTO {
    private String description;;
    private String address;
    private double maxHourlyConsumption;
    Long userId;
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
