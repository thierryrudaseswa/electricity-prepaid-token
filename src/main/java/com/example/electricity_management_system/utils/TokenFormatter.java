package com.example.electricity_management_system.utils;

public class TokenFormatter {
    public static String formatToken(String rawToken) {
        if (rawToken != null && rawToken.length() == 16 && rawToken.matches("\\d{16}")) {
            return rawToken.replaceAll("(\\d{4})(\\d{4})(\\d{4})(\\d{4})", "$1-$2-$3-$4");
        }
        return rawToken;
    }
}