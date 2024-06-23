package su.soviet.PickUp.Point.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import su.soviet.PickUp.Point.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
