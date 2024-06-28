package su.soviet.PickUp.Point.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import su.soviet.PickUp.Point.model.User;
import su.soviet.PickUp.Point.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

    @Autowired
    private UserService service;

    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser(Authentication auth){
        User currentUser = service.getUserByName(auth.getName());
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/admin/new-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = service.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = service.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/admin/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User updateData) {
        User updatedUser = service.updateUser(updateData);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/admin/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/")
    public ModelAndView getDefaultPage() {
        return new ModelAndView("order_pick_up");
    }
}
