package com.example.electricity_management_system.controller;

import com.example.electricity_management_system.dto.PurchasedTokenRegistrationDto;
import com.example.electricity_management_system.dto.PurchasedTokenUpdateDto;
import com.example.electricity_management_system.dto.SuccessResponse;
import com.example.electricity_management_system.model.PurchasedTokenModel;
import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.repository.UserRepository;
import com.example.electricity_management_system.service.PurchasedTokenServices;
import com.example.electricity_management_system.service.UserServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/tokens")
@Tag(name="Token")
public class PurchasedTokenController {
    private final PurchasedTokenServices purchasedTokenServices;
    private final UserRepository userRepository;

    public PurchasedTokenController(PurchasedTokenServices purchasedTokenServices, UserRepository userRepository) {
        this.purchasedTokenServices = purchasedTokenServices;
        this.userRepository = userRepository;

    }
    @PostMapping("/purchase")
   public ResponseEntity<SuccessResponse<PurchasedTokenModel>> purchaseToken(@RequestBody PurchasedTokenRegistrationDto tokenInfo) throws BadRequestException {
        PurchasedTokenModel token=purchasedTokenServices.createToken(tokenInfo);
        return ResponseEntity.ok().body(new SuccessResponse<>("Token Purchased Successfully",token));
   }
   @GetMapping("{meterNumber}")
    public ResponseEntity<SuccessResponse<List<PurchasedTokenModel>>> getTokenByMeterNumber(@Valid @PathVariable Long meterNumber) throws BadRequestException {
        return ResponseEntity.ok().body(new SuccessResponse<List<PurchasedTokenModel>>("Token retrieved successfully",purchasedTokenServices.getTokenByMeterNumber(meterNumber)));
   }
    @PostMapping("/validate/{token}")
    public ResponseEntity<SuccessResponse<PurchasedTokenModel>> validateToken(@PathVariable String token) throws BadRequestException {
        return ResponseEntity.ok().body(purchasedTokenServices.getTOkenByToken(token));
    }
    @GetMapping("/all")
    public ResponseEntity<SuccessResponse<List<PurchasedTokenModel>>> getAllTokens() {
        return ResponseEntity.ok().body(new SuccessResponse<>("All tokens retrieved successfully",purchasedTokenServices.getAllTokens()));
    }
    @PutMapping("/{token}")
    public ResponseEntity<SuccessResponse<PurchasedTokenModel>> updateToken(@PathVariable String token,@Valid @RequestBody PurchasedTokenUpdateDto tokenInfo) {
    return ResponseEntity.ok().body(new SuccessResponse<>("Token Updated Successfully",purchasedTokenServices.updateTokenStatus(token,tokenInfo)));
    }
    @GetMapping
    public ResponseEntity<SuccessResponse<List<PurchasedTokenModel>>> getTokenForUser() {
        Authentication authenticate= SecurityContextHolder.getContext().getAuthentication();
        String email=authenticate.getName();
        UserModel user=userRepository.findByEmail(email);
        return ResponseEntity.ok().body(new SuccessResponse<>("All tokens retrieved successfully",purchasedTokenServices.getTokenByUserId(user)));
    }


}
