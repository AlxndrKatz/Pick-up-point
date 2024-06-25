package su.soviet.PickUp.Point.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import su.soviet.PickUp.Point.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/myorders/")
    public ModelAndView getDefaultPage() {
        return new ModelAndView("myorders");
    }
}
