package com.example.electricity_management_system.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeterRegistrationAndUpdateDto {
    @NotNull
    private Long userId;


}
