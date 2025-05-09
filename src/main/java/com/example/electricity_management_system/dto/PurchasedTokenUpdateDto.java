package com.example.electricity_management_system.dto;

import com.example.electricity_management_system.utils.TokenStatus;
import jakarta.validation.constraints.NotNull;

public class PurchasedTokenUpdateDto {

    @NotNull(message = "Please provide token status")
    public TokenStatus status;
}
