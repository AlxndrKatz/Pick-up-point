package su.soviet.PickUp.Point.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import su.soviet.PickUp.Point.dto.CurrentUserDTO;
import su.soviet.PickUp.Point.dto.UserDTO;
import su.soviet.PickUp.Point.model.User;
import su.soviet.PickUp.Point.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pick-up-point")
public class AdminController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/")
    public ModelAndView getDefaultPage() {
        return new ModelAndView("order_pick_up");
    }

    @GetMapping("/current-user")
    public ResponseEntity<CurrentUserDTO> getCurrentUser(Authentication auth) {
        User currentUser = service.getUserByName(auth.getName());
        CurrentUserDTO currentUserDto = service.mapToCurrentUserDTO(currentUser);
        return new ResponseEntity<>(currentUserDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/new-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = service.getUsers();
        List<UserDTO> dtos = users.stream().map(service::mapToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User updateData) {
        User updatedUser = service.updateUser(updateData);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
