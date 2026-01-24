package com.mihirrueben.Recipe_app.controller;

import com.mihirrueben.Recipe_app.model.User;
import com.mihirrueben.Recipe_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")

public class UserController {

    @Autowired
    private UserService userService;

    //GET user profile
    @GetMapping("/{id}")
    public ResponseEntity<User> getUSerProfile(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // UPDATE a user's profile
    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable String id, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }
}