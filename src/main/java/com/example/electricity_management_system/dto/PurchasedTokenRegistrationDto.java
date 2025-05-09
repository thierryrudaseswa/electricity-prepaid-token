package com.example.electricity_management_system.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class PurchasedTokenRegistrationDto {
    @NotNull(message = "Please provide meter number")
    @Pattern(regexp = "\\d{6}", message = "Meter number should be 6 digits")
    public Long meterNumber;
    @Min(value = 100,message = "Minimum amount is 100 RWF for 1 day")
    @Max(value=182500,message="Maximum amount is 182500 RWF for 5 years")
    public int amount;
}
