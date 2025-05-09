package com.example.electricity_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class LoginDto {
    @NotNull(message = "Please provide email")
    @Email(message = "Invalid Email")
    public String email;
    @NotNull(message = "Please provide password")
    public String password;
}
