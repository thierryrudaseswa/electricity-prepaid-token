package com.example.electricity_management_system.dto;

import com.example.electricity_management_system.model.MeterModel;
import com.example.electricity_management_system.utils.Role;
import com.example.electricity_management_system.validator.ValidRwandaId;
import com.example.electricity_management_system.validator.ValidRwandanPhoneNumber;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserRegisterDto {
    @NotNull(message="Please provide name")
    public String names;
    @NotNull(message = "Please provide a password")

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
    )
    public String password;
    @ValidRwandanPhoneNumber
    public String phoneNumber;
    @Email(message = "Invalid Email")
    public String email;
   @ValidRwandaId
    public String nationalID;

    public Role role;

}
