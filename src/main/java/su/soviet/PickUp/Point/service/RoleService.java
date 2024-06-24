package su.soviet.PickUp.Point.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import su.soviet.PickUp.Point.dao.RoleRepository;
import su.soviet.PickUp.Point.model.Role;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repo;

    public Set<Role> setDefaultRole() {
        Role defaultRole = repo.findById(1L).orElse(null);
        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(defaultRole);
        return defaultRoles;
    }
}
