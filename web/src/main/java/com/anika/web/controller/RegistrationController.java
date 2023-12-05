package com.anika.web.controller;

import com.anika.core.entity.User;
import com.anika.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam(value = "username", required = true) String username,
                                               @RequestParam(value = "password", required = true) String password,
                                               @RequestParam(value = "email", required = true) String email) {
        Optional<User> userOptional = userService.findUserByNameOrEmail(username, email);
        if(userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User with this email or username already exists");
        } else {
            userService.saveUser(new User(username, "{noop}" + password, email));
            return ResponseEntity.ok("Success");
        }
    }
}
