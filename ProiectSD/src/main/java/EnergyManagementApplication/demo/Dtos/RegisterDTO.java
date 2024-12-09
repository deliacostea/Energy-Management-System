package EnergyManagementApplication.demo.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String role;
}
