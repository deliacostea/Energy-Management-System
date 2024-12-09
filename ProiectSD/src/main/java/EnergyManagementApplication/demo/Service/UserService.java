package EnergyManagementApplication.demo.Service;

import EnergyManagementApplication.demo.Dtos.*;
import EnergyManagementApplication.demo.Model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    LoginResponseDTO login(LoginDTO loginDTO);
    RegisterResponseDTO register(RegisterDTO registerDTO);
    List<User> getUseri();
    void deleteUserById(long id);
    User getUserById(long id);
    User updateUser(User user);

    public User updateUser(Long id, UserDTO userDTO);


    User getUserByEmail(String email);

}
