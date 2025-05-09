package com.example.electricity_management_system.dto;

import com.example.electricity_management_system.model.UserModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponseDto {
    String token;
    String message;
    public AuthResponseDto(String token, String message) {
        this.token = token;
        this.message = message;

    }
}
