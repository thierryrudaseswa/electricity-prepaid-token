package com.example.electricity_management_system.service;

import com.example.electricity_management_system.dto.UserRegisterDto;
import com.example.electricity_management_system.dto.UserUpdateDto;
import com.example.electricity_management_system.email.EmailService;
import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.repository.UserRepository;
import com.example.electricity_management_system.utils.PasswordVerifiedStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class UserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserServices(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }
    public UserModel createUser(UserRegisterDto user){
        UserModel userModel=new UserModel(user);
        userModel.password=passwordEncoder.encode(userModel.password);
        return  userRepository.save(userModel);
    }
    public List<UserModel> getAllUsers(){
        return  userRepository.findAll();
    }
    public UserModel getUserById(Long id){
        Optional<UserModel> user= userRepository.findById(id);
        return user.orElse(null);
    }
    public String deleteUserById(Long id){
        Optional<UserModel> user=userRepository.findById(id);
        if(user.isPresent()){
            return "User Deleted successfully";
        }
        else{
            return "User not found";
        }
    }
    public void requestResetPassword(  String email){
        UserModel user=userRepository.findByEmail(email);
        if(user!=null){
            String otp = String.format("%06d", (int) (Math.random() * 900000 + 100000));
            user.passwordVerificationOtpSentDate= LocalDateTime.now();
            user.passwordVerificationCode=Integer.parseInt(otp);
            emailService.sendResetPasswordOtp(user.email,user.names,otp);
            userRepository.save(user);


        }
        else{
            throw new EntityNotFoundException("User not found with that email");
        }
    }
    public void verifyResetPassword(String email,String otp){
        UserModel user=userRepository.findByEmail(email);
        if(user!=null){

            boolean isOtpCorrect=user.passwordVerificationCode==Integer.parseInt(otp);
            if(isOtpCorrect){

                user.passwordVerifiedStatus= PasswordVerifiedStatus.VERIFIED;
                userRepository.save(user);
            }
            else{
                throw new EntityNotFoundException("Invalid OTP");
            }

        }
        else{
            throw new EntityNotFoundException("User not found with that email");
        }
    }

    public void resetPassword(String email,String password){
        UserModel user=userRepository.findByEmail(email);
        if(user!=null){
            if (user.passwordVerifiedStatus != PasswordVerifiedStatus.VERIFIED) {
                throw new EntityNotFoundException("Request reset password first");
            }
            else{
                if(user.passwordVerificationOtpSentDate.plusMinutes(5).isBefore(LocalDateTime.now())){
                    throw new IllegalArgumentException("OTP has expired");
                }
                user.password= passwordEncoder.encode(password);

                user.passwordVerifiedStatus= PasswordVerifiedStatus.NOT_VERIFIED;
                userRepository.save(user);
            }
        }
        else{
            throw new EntityNotFoundException("User not found with that email");
        }
    }
    @SneakyThrows
    public void verifyEmail(String email, String otp){
        UserModel user=userRepository.findByEmail(email);
        if(user!=null){
            if(user.emailVerificationOtpSentDate.plusMinutes(5).isBefore(LocalDateTime.now())){
                throw new BadRequestException("OTP has expired");
            }

            boolean isOtpCorrect=user.emailVerificationCode==Integer.parseInt(otp);
            if(isOtpCorrect){
                user.emailVerified=true;

                userRepository.save(user);
            }

            else{
                throw new EntityNotFoundException("Invalid OTP");
            }

        }
        else{
            throw new AccessDeniedException("User not found with that email");
        }
    }
    public void requestEmailVerification(String email) throws BadRequestException {
        System.out.println(email);
        UserModel user=userRepository.findByEmail(email);
        if(user!=null){
            if(!user.emailVerified){
                user.emailVerificationOtpSentDate=LocalDateTime.now();
                String otp = String.format("%06d", (int) (Math.random() * 900000 + 100000));
                user.emailVerificationCode=Integer.parseInt(otp);
                emailService.sendAccountVerificationEmail(user.email,user.names,otp);
                userRepository.save(user);
            }
            else{
                throw new BadRequestException("Email already verified");
            }


        }
        else{
            throw new AccessDeniedException("User not found with that email");
        }
    }


    public UserModel updateUser(Long id,UserUpdateDto userRequest){
        Optional<UserModel>optionalUser=userRepository.findById(id);
        if(optionalUser.isPresent()){
            UserModel user=optionalUser.get();
            if(!userRequest.email.isEmpty()){
                user.email=userRequest.email;
            }
            if(!userRequest.names.isEmpty()){
                user.email=userRequest.email;
            }
            if(!userRequest.phoneNumber.isEmpty()){
                user.email=userRequest.email;
            }
            if(!userRequest.nationalID.isEmpty()){
                user.email=userRequest.email;
            }
            return userRepository.save(user);
        }
        else{
            return null;
        }
    }



}
