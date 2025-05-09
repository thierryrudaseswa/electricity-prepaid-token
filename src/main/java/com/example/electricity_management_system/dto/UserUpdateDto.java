package com.example.electricity_management_system.dto;

import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    public String names;

    @Pattern(
            regexp = "^\\+2507[8-9]\\d{7}$",
            message = "Please provide a valid 10-digit  phone number"
    )
    public String phoneNumber;
    @Email(message = "Invalid Email")
    public String email;
    @Pattern(regexp = "\\d{16}", message = "Invalid ID(National Id should be 16 digits)")
    public String nationalID;


}
