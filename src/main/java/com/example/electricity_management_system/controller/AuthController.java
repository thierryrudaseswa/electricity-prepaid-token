package com.example.electricity_management_system.controller;

import com.example.electricity_management_system.dto.*;
import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.security.JwtProvider;
import com.example.electricity_management_system.security.UserServiceImplementation;
import com.example.electricity_management_system.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth")
@SecurityRequirements({})

public class AuthController {
    private final UserServiceImplementation userServiceImplementation;
    private final PasswordEncoder passwordEncoder;
    public final UserServices userServices;

    public AuthController(UserServices userServices, UserServiceImplementation userServiceImplementation, PasswordEncoder passwordEncoder) {
        this.userServices = userServices;
        this.userServiceImplementation=userServiceImplementation;
        this.passwordEncoder=passwordEncoder;
    }
   @Operation(summary = "Register",description = "Register to the system",responses = {
           @ApiResponse(responseCode = "201",description = "User Created Successfully"),
           @ApiResponse(responseCode = "400",description = "Invalid Request",content = @Content(mediaType = "application/json",schema = @Schema())),
   })
    @PostMapping("/register")
    ResponseEntity<RegisterResponseDto> createUser(@Valid @RequestBody UserRegisterDto user){
        UserModel newUser=userServices.createUser(user);
        UserDetails userDetails=userServiceImplementation.loadUserByUsername(newUser.getEmail());
        Authentication auth=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        String accessToken= JwtProvider.generateToken(auth);
        return ResponseEntity.status(201).body(new RegisterResponseDto(newUser,accessToken,"User Created Successfully"));
    }
    @Operation(summary = "Login",description = "Login to the system",responses = {
            @ApiResponse(responseCode = "200",description = "Login successfully"),
            @ApiResponse(responseCode = "401",description = "Invalid Username or password",content = @Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "500",description = "Something went wrong",content = @Content(mediaType = "application/json",schema = @Schema()))
    })
    //     @RateLimiter(name = "auth-rate-limiter")
    @PostMapping("/login")
    ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto){
        UserDetails userDetails= userServiceImplementation.loadUserByUsername(loginDto.email);
        if(userDetails==null){
            System.out.println("User not found");
            throw new BadCredentialsException("Invalid Username or password");
        }
        if(!passwordEncoder.matches(loginDto.password,userDetails.getPassword())){
            System.out.println(userDetails.getPassword());
            throw new BadCredentialsException("Invalid Username or password");
        }
        Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,loginDto.password,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= JwtProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponseDto(token,"Login successfully"));
    }
    @PostMapping("/request-reset-password")
    ResponseEntity<SuccessResponse<String>> requestResetPassword(@Valid  @RequestBody EmailDto emailDto){
        userServices.requestResetPassword(emailDto.email);
        return ResponseEntity.ok().body(new SuccessResponse<>("OTP sent successfully"));
    }
    @PostMapping("/verify-reset-password")
    ResponseEntity<SuccessResponse<String>> verifyRequestPassword(@Valid @RequestBody VerifyDto verifyDto ){
        userServices.verifyResetPassword(verifyDto.email,verifyDto.otp);
        return ResponseEntity.ok().body(new SuccessResponse<>("OTP verified successfully"));

    }
    @PostMapping("/reset-password")
    ResponseEntity<SuccessResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto){
        userServices.resetPassword(resetPasswordDto.email,resetPasswordDto.password);
        return ResponseEntity.ok().body(new SuccessResponse<>("Password reset successfully"));
    }
    @PostMapping("/request-email")
    ResponseEntity<SuccessResponse<String>> requestResetEmail( @Valid  @RequestBody EmailDto emailDto) throws BadRequestException {
        userServices.requestEmailVerification(emailDto.email);
        return ResponseEntity.ok().body(new SuccessResponse<>("Email sent successfully"));
    }
    @PostMapping("/verify-email")
    ResponseEntity<SuccessResponse<String>> verifyEmail(@Valid  @RequestBody VerifyDto verifyDto ){
        userServices.verifyEmail(verifyDto.email,verifyDto.otp);
        return ResponseEntity.ok().body(new SuccessResponse<>("Email verified successfully"));
    }




}
