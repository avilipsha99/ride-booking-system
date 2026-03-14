package com.ridebooking.userservice.dto;

import com.ridebooking.userservice.model.User;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private User.Role role;
}
