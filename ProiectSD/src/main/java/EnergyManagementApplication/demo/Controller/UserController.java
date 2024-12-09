package EnergyManagementApplication.demo.Controller;

import EnergyManagementApplication.demo.Dtos.LoginDTO;
import EnergyManagementApplication.demo.Dtos.LoginResponseDTO;
import EnergyManagementApplication.demo.Dtos.RegisterDTO;
import EnergyManagementApplication.demo.Dtos.RegisterResponseDTO;
import EnergyManagementApplication.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/useri")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = userService.login(loginDTO);
        if (loginResponseDTO.getMesaj().equals("Logare cu succes!")) {
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(loginResponseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        RegisterResponseDTO registerResponseDTO = userService.register(registerDTO);
        if (registerResponseDTO.getUser() != null) {
            return new ResponseEntity<>(registerResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(registerResponseDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
