package su.soviet.PickUp.Point.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import su.soviet.PickUp.Point.dao.UserRepository;
import su.soviet.PickUp.Point.dto.CurrentUserDTO;
import su.soviet.PickUp.Point.dto.CustomerDTO;
import su.soviet.PickUp.Point.dto.UserDTO;
import su.soviet.PickUp.Point.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleService roleService;

    public User createUser(User user) {
        Optional<User> userByName = userRepo.getUserByName(user.getName());
        Optional<User> userByEmail = userRepo.getUserByEmail(user.getEmail());
        if (userByName.isEmpty() && userByEmail.isEmpty()) {
            user.setRoles(roleService.setDefaultRole());
            return userRepo.save(user);
        }
        return null;
    }

    public User getUserById(Long id) {
        if (userRepo.existsById(id)) {
            return userRepo.findById(id).orElse(null);
        }
        return null;
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }

    public void deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        }
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepo.getUserByName(name).orElseThrow(() -> new UsernameNotFoundException("User not found " + name));
    }

    public User getUserByName(String name) {
        return userRepo.getUserByName(name).orElseThrow(() -> new UsernameNotFoundException("User not found " + name));
    }

    public UserDTO mapToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getAge(),
                user.getEmail(), user.getPassword(), user.getRoles());
    }

    public CurrentUserDTO mapToCurrentUserDTO(User user) {
        return new CurrentUserDTO(user.getId(), user.getName(), user.getRoles());
    }

    public CustomerDTO mapToCustomerDTO(User user) {
        return new CustomerDTO(user.getId(), user.getOrders());
    }
}
