package com.example.electricity_management_system.service;

import com.example.electricity_management_system.dto.MeterRegistrationAndUpdateDto;
import com.example.electricity_management_system.model.MeterModel;
import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.repository.MeterRepository;
import com.example.electricity_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class MeterServices {

    private final MeterRepository meterRepository;
    private final UserRepository userRepository;
    private static final String CHARACTERS = "0123456789";
    private static final int LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();


    public MeterServices(MeterRepository meterRepository,UserRepository userRepository) {
        this.meterRepository = meterRepository;
        this.userRepository=userRepository;
    }

    public List<MeterModel> getAllMeters(){
        return  meterRepository.findAll();
    }
    public List<MeterModel> getMetersByUser(UserModel user){
        return  meterRepository.findMeterModelByUser(user);
    }
    public MeterModel getMeterById(Long id){
        return meterRepository.findMeterModelById(id);
    }

    public String deleteMeterByMeterNumber(Long meterNumber){
        if(meterRepository.existsMeterModelByMeterNumber(meterNumber)){
            meterRepository.deleteByMeterNumber(meterNumber);
            return  "Meter Deleted successfully";
        }
        else{
            return "Meter not found";
        }

    }
    public String deleteMeterByMeterId(Long id){
        Optional<MeterModel> optionalMeter=meterRepository.findById(id);
        if(optionalMeter.isPresent()){
            meterRepository.deleteById(id);
            return  "Meter Deleted successfully";
        }
        else{
            return "Meter not found";
        }
    }

    public MeterModel createMeter(MeterRegistrationAndUpdateDto meterRegistrationDto) {
        Optional<UserModel> optionalUser = userRepository.findById(meterRegistrationDto.getUserId());
        long meterNumber= Long.parseLong(this.generateMeterNumber());
        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            MeterModel meter = new MeterModel();
            meter.meterNumber = meterNumber;
            meter.user = user;
            return meterRepository.save(meter);
        } else {
            return null;
        }
    }
    public String generateMeterNumber() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        boolean tokeAlreadyExist=meterRepository.existsMeterModelByMeterNumber(Long.valueOf(sb.toString()));
        if(tokeAlreadyExist){
            return generateMeterNumber();
        }
        return sb.toString();
    }
//    public MeterModel updateMeter(MeterRegistrationAndUpdateDto updateDto){
//        Optional<UserModel> optionalUser = userRepository.findById(updateDto.getUserId());
//        Optional<MeterModel> optionalMeter=meterRepository.findMeterModelByMeterNumber(updateDto.getMeterNumber());
//
//        if (optionalUser.isPresent()&&optionalMeter.isPresent()) {
//            UserModel user = optionalUser.get();
//
//            MeterModel meterModel=optionalMeter.get();
//            meterModel.user=user;
//            meterRepository.save(meterModel);
//            return  meterModel;
//        } else {
//            return null;
//        }
//
//    }

}