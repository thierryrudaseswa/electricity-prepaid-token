package com.example.electricity_management_system.dto;

import com.example.electricity_management_system.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterResponseDto {
    private UserModel user;
    private String token;
    private String message;
}
