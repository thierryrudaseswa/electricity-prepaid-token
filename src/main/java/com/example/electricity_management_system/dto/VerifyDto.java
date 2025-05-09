package com.example.electricity_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyDto {
    @NotNull(message = "Email is required")
    @Email(message="Invalid Email")
    public String email;
    @NotNull(message = "Token is required")
    @Length(min = 6, max = 6, message = "Invalid token")
    public String otp;
}
