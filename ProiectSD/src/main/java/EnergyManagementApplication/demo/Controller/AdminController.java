package EnergyManagementApplication.demo.Controller;

import EnergyManagementApplication.demo.Dtos.RegisterDTO;
import EnergyManagementApplication.demo.Dtos.RegisterResponseDTO;
import EnergyManagementApplication.demo.Dtos.UserDTO;
import EnergyManagementApplication.demo.Model.User;
import EnergyManagementApplication.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://frontend.localhost")

@RequestMapping("/admin")

public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user")
    public ResponseEntity<User> addUser(@RequestBody RegisterDTO registerDTO) {
        RegisterResponseDTO response = userService.register(registerDTO);
        if (response.getUser() != null) {
            return new ResponseEntity<>(response.getUser(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUseri();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



   @PutMapping("/user/update/{id}")
   public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
       User updatedUser = userService.updateUser(id, userDTO);
       return new ResponseEntity<>(updatedUser, HttpStatus.OK);
   }



    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("Utilizatorul a fost șters cu succes.", HttpStatus.OK);
    }
}
