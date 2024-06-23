package su.soviet.PickUp.Point.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import su.soviet.PickUp.Point.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByName(String name);

    Optional<User> getUserByEmail(String email);
}
