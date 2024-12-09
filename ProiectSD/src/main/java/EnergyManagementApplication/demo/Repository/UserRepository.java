package EnergyManagementApplication.demo.Repository;

import EnergyManagementApplication.demo.Model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByRole(String role);
}
