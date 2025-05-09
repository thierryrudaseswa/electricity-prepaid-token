package com.example.electricity_management_system.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.utils.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {
    private final UserModel user;
    public CustomUserDetail(UserModel user){
        this.user=user;
    }

    @Override
    public String getUsername(){
        return user.getEmail();
    }
    @Override
    public String getPassword(){
        return user.getPassword();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
    public String getFullName() {
        return user.getNames();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Replace this with actual role/authority logic based on your UserModel
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.role.toString()));
        return authorities;
    }


}