package com.example.electricity_management_system.security;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class JwtConstant {
    public static final SecretKey JWT_KEY = Jwts.SIG.HS512.key().build();
    public static final String JWT_HEADER = "Authorization";
}