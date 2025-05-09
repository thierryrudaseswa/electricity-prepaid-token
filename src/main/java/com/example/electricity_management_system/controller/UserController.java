package com.example.electricity_management_system.controller;


import com.example.electricity_management_system.dto.UserUpdateDto;
import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.service.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User")

@Validated
public class UserController {
    private final UserServices userServices;


    public UserController(UserServices userServices) {
        this.userServices = userServices;

    }
    @Operation(
    summary = "Get All Users",
            description = "Retrieve all users",

            responses = {
            @ApiResponse(responseCode = "200", description = "Users Found"),
                    @ApiResponse(responseCode = "500",description = "Something wnet wrong")

            }

    )

    @GetMapping("/all")
    ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.ok(userServices.getAllUsers());
    }
    @GetMapping("/{id}")
    ResponseEntity<UserModel> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userServices.getUserById(id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userServices.deleteUserById(id));
    }
    @PutMapping("/{id}")
    ResponseEntity<UserModel> updateUser(@Valid @PathVariable Long id, @Valid @RequestBody UserUpdateDto user){
        return ResponseEntity.ok(userServices.updateUser(id,user));
    }




}
