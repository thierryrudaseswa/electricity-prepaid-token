package com.example.electricity_management_system.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.crypto.SecretKey;
import java.util.Date;
@AllArgsConstructor
@Getter
@Setter
public class JwtUtils {
    private final Claims claims;
    private final SecretKey secretKey;
    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }
    public Integer getUserId(){
        return Integer.parseInt(claims.getSubject());
    }
    public Role getRole(){
        return Role.valueOf(claims.get("role",String.class));
    }
    public String toString(){
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }

}
