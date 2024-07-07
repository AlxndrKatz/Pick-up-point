package su.soviet.PickUp.Point.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import su.soviet.PickUp.Point.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}