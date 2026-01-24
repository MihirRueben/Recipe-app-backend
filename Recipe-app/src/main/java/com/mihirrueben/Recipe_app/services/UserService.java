package com.mihirrueben.Recipe_app.services;

import com.mihirrueben.Recipe_app.dto.UserDTO;
import com.mihirrueben.Recipe_app.model.User;
import com.mihirrueben.Recipe_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);


        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public UserDTO getUserProfile(String id) {
        User user = getUserById(id); // Reusing the private helper below
        return convertToDTO(user);
    }

    public User updateUser(String id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setBio(userDetails.getBio());
        user.setProfilePictureUrl(userDetails.getProfilePictureUrl());
        return userRepository.save(user);
    }



    private User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBio(user.getBio());
        dto.setProfilePictureUrl(user.getProfilePictureUrl());
        return dto;
    }
}