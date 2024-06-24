package su.soviet.PickUp.Point.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import su.soviet.PickUp.Point.model.Order;
import su.soviet.PickUp.Point.model.User;
import su.soviet.PickUp.Point.service.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService service;


    @PreAuthorize("hasAnyAuthority('ROLE_EMPLOYEE', 'ROLE_USER')")
    @GetMapping("/myorders/user/{id}")
    public ResponseEntity<Set<Order>> getUserOrders(@PathVariable(value = "id") Long userId) {
        User user = service.getUserById(userId);
        Set<Order> orders = user.getOrders();
       if (orders.isEmpty()) {
           return null;
       } else {
           return new ResponseEntity<>(orders ,HttpStatus.OK);
       }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/myorders/")
    public ModelAndView getDefaultPage() {
        return new ModelAndView("myorders");
    }
}
