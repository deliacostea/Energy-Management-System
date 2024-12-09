package EnergyManagementApplication.demo.Dtos;

import EnergyManagementApplication.demo.Model.User;
import lombok.Data;

@Data
public class LoginResponseDTO {
    private User user;
    private String mesaj;
}
