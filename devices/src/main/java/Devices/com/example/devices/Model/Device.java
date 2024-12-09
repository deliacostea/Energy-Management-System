package Devices.com.example.devices.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name="devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private String address;
    private double maxHourlyConsumption;
    private Long userId;


    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", maxHourlyConsumption=" + maxHourlyConsumption +
                '}';
    }
}
