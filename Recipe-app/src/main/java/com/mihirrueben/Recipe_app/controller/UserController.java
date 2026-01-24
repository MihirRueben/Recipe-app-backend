package com.mihirrueben.Recipe_app.controller;

import com.mihirrueben.Recipe_app.dto.UserDTO;
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
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable String id) {
        // The service now returns a UserDTO, keeping the password safe!
        return ResponseEntity.ok(userService.getUserProfile(id));
    }

    // UPDATE a user's profile
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable String id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);

        // Convert the updated entity to DTO before sending it back!
        UserDTO response = new UserDTO();
        response.setId(updatedUser.getId());
        response.setUsername(updatedUser.getUsername());
        response.setEmail(updatedUser.getEmail());
        response.setBio(updatedUser.getBio());
        response.setProfilePictureUrl(updatedUser.getProfilePictureUrl());

        return ResponseEntity.ok(response);
    }
}