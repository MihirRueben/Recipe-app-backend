package com.mihirrueben.Recipe_app.controller;

import com.mihirrueben.Recipe_app.model.User;
import com.mihirrueben.Recipe_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private UserService userService;

    //REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // LOGIN
    @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody User loginRequest) {

            System.out.println("Login Attempt for email: " + loginRequest.getEmail());
            Optional<User> user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
            }
        }

}