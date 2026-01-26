package com.mihirrueben.Recipe_app.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String profilePictureUrl;
}