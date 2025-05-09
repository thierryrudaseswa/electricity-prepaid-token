package com.example.electricity_management_system.service;
import com.example.electricity_management_system.dto.PurchasedTokenRegistrationDto;
import com.example.electricity_management_system.dto.PurchasedTokenUpdateDto;
import com.example.electricity_management_system.dto.SuccessResponse;
import com.example.electricity_management_system.model.MeterModel;
import com.example.electricity_management_system.model.PurchasedTokenModel;
import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.repository.MeterRepository;
import com.example.electricity_management_system.repository.PurchasedTokenRepository;
import com.example.electricity_management_system.repository.UserRepository;
import com.example.electricity_management_system.utils.TokenStatus;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
@Service
public class PurchasedTokenServices {
  private final PurchasedTokenRepository purchasedTokenRepository;
    private static final String CHARACTERS = "0123456789";
    private static final int LENGTH = 16;
    private static final SecureRandom RANDOM = new SecureRandom();
    private final MeterRepository meterRepository;
    private final UserRepository userModelRepository;

    public PurchasedTokenServices(PurchasedTokenRepository purchasedTokenRepository, MeterRepository meterRepository, UserRepository userModelRepository) {
    this.purchasedTokenRepository = purchasedTokenRepository;
    this.meterRepository = meterRepository;
        this.userModelRepository = userModelRepository;
    }
  public PurchasedTokenModel createToken(PurchasedTokenRegistrationDto token) throws BadRequestException {
      PurchasedTokenModel tokenModel = new PurchasedTokenModel();
      tokenModel.amount = token.amount;
      MeterModel meter = meterRepository.findMeterModelByMeterNumber(token.meterNumber).orElse(null);
      if (meter == null) {
         throw new BadRequestException("Meter not found");
      }
      tokenModel.meterNumber = meter;
      tokenModel.token = this.generateToken();
      long days = (long) (token.amount / 100);
      tokenModel.tokenValueDays = days;
      return purchasedTokenRepository.save(tokenModel);

  }
  public SuccessResponse<PurchasedTokenModel> getTOkenByToken(String token){
         Optional<PurchasedTokenModel> purchasedToken=purchasedTokenRepository.findByToken(token.replaceAll("-",""));
         String message="";
         if(purchasedToken.get().tokenStatus.equals(TokenStatus.NEW)){
             message="Token Is Ready to be used";
         }
         else if(purchasedToken.get().tokenStatus.equals(TokenStatus.USED)){
             message="Token Already Used";
         }
         else{
             message="Token Expired please renew your token";
         }
         return new SuccessResponse<>(message,purchasedToken.orElse(null));
  }
  public PurchasedTokenModel updateTokenStatus(String token, PurchasedTokenUpdateDto tokenInfo){
      Optional<PurchasedTokenModel> purchasedToken=purchasedTokenRepository.findByToken(token.replaceAll("-",""));
      if(purchasedToken.isPresent()) {
          PurchasedTokenModel tokenModel = purchasedToken.get();
          tokenModel.tokenStatus = tokenInfo.status;
          return purchasedTokenRepository.save(tokenModel);
      }
      else{
          throw new EntityNotFoundException("No token found with token number: "+token);
      }
  }
    public String generateToken() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        boolean tokeAlreadyExist=purchasedTokenRepository.existsByToken(sb.toString());
        if(tokeAlreadyExist){
            return generateToken();
        }
        return sb.toString();
    }
    public PurchasedTokenModel getTokenByTokenNumber(String token){
        Optional<PurchasedTokenModel> purchasedToken=purchasedTokenRepository.findByToken(token.replaceAll("-",""));
        return purchasedToken.orElse(null);
    }
    public PurchasedTokenModel updateTokenByTokenNumber(String token, String status){
      Optional<PurchasedTokenModel> purchasedToken=purchasedTokenRepository.findByToken(token.replaceAll("-",""));
      if(purchasedToken.isPresent()) {
          PurchasedTokenModel tokenModel = purchasedToken.get();
          tokenModel.tokenStatus = TokenStatus.valueOf(status);
          return purchasedTokenRepository.save(tokenModel);

      }
      return null;
    }

    public List<PurchasedTokenModel> getTokenByMeterNumber(Long meterNumber) throws BadRequestException {
        Optional<MeterModel> meter=meterRepository.findMeterModelByMeterNumber(meterNumber);
        if(meter.isEmpty()){
            throw new BadRequestException("Invalid Meter Number");
        }
        return purchasedTokenRepository.findByMeterNumber(meter.orElse(null));

    }
    public List<PurchasedTokenModel> getAllTokens(){
        return purchasedTokenRepository.findAll();
    }
    public List<PurchasedTokenModel> getTokenByUserId(UserModel  user){
        List<PurchasedTokenModel> allTokens=new java.util.ArrayList<>();
        List<MeterModel> meters=meterRepository.findMeterModelByUser(user);
        if(meters.isEmpty()){
            return null;
        }
       else{
           for(MeterModel meter:meters){
               List<PurchasedTokenModel> tokens=purchasedTokenRepository.findByMeterNumber(meter);
               if(tokens.isEmpty()){
                   return null;
               }
               else{
                   allTokens.addAll(tokens);
               }
           }
        }
       return allTokens;
    }
}
