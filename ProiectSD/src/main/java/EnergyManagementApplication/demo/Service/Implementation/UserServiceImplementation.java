package EnergyManagementApplication.demo.Service.Implementation;

import EnergyManagementApplication.demo.Dtos.*;
import EnergyManagementApplication.demo.Model.User;
import EnergyManagementApplication.demo.Repository.UserRepository;
import EnergyManagementApplication.demo.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;


    public UserServiceImplementation(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;

    }
    @PostConstruct
    public void createDefaultAdmin() {

        if (userRepository.findByRole("admin").isEmpty()) {
            User adminUser = User.builder()
                    .name("Admin")
                    .email("admin@yahoo.com")
                    .password(criptareParola("admin"))
                    .role("admin")
                    .build();
            userRepository.save(adminUser);
            System.out.println("Adminul implicit a fost creat.");
        }
    }


    @Override
    public LoginResponseDTO login(LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        User user = userRepository.findByEmail(loginDTO.getEmail());
        if(user == null) {
            loginResponseDTO.setUser(null);
            loginResponseDTO.setMesaj("Nu exista cont cu acest email");

        }else{
            if(criptareParola(loginDTO.getPassword()).equals(user.getPassword()))
            {
                loginResponseDTO.setUser(user);
                loginResponseDTO.setMesaj("Logare cu succes!");

            }else{
                loginResponseDTO.setUser(null);
                loginResponseDTO.setMesaj("Email-ul sau parola sunt gresite!");
            }
        }
        return loginResponseDTO;
    }

    @Override
    public RegisterResponseDTO register(RegisterDTO registerDTO) {
        RegisterResponseDTO response = new RegisterResponseDTO();

        if (userRepository.findByEmail(registerDTO.getEmail()) != null) {
            response.setMesaj("Email deja utilizat!");
            response.setUser(null);
            return response;
        }


        String role = registerDTO.getRole() != null ? registerDTO.getRole() : "client";

        User newUser = User.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(criptareParola(registerDTO.getPassword()))
                .role(role)
                .build();

        userRepository.save(newUser);

        response.setUser(newUser);
        response.setMesaj("Utilizator înregistrat cu succes!");
        return response;
    }

    @Override
    public List<User> getUseri() {
        List<User>useri=(List<User>)userRepository.findAll();
        useri.removeIf(user -> user.getRole().equals("admin"));
        return useri;
    }

    @Override
    public void deleteUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizatorul cu id-ul " + id + " nu a fost găsit"));
        userRepository.delete(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizatorul cu id-ul " + id + " nu a fost găsit"));
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit"));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(criptareParola(userDTO.getPassword()));
        }


        return userRepository.save(existingUser);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Utilizatorul cu email-ul " + email + " nu a fost găsit");
        }
        return user;
    }
    private String criptareParola(String password) {
        try {
            // Sercured Hash Algorithm - 256
            // 1 byte = 8 biți
            // 1 byte = 1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
