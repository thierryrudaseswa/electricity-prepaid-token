package com.example.electricity_management_system.utils;

import com.example.electricity_management_system.model.PurchasedTokenModel;
import com.example.electricity_management_system.repository.PurchasedTokenRepository;

import java.security.SecureRandom;

public class TokenGenerator {
    private final PurchasedTokenRepository purchasedTokenRepository;
    public TokenGenerator(PurchasedTokenRepository purchasedTokenRepository) {
        this.purchasedTokenRepository = purchasedTokenRepository;
    }




}