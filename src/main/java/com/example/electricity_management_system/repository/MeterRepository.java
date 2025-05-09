package com.example.electricity_management_system.repository;

import com.example.electricity_management_system.model.MeterModel;
import com.example.electricity_management_system.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeterRepository extends JpaRepository<MeterModel,Long> {
    Optional<MeterModel> findMeterModelByMeterNumber(Long meterNumber);
    List<MeterModel> findMeterModelByUser(UserModel user);
    MeterModel findMeterModelById(Long id);
    boolean existsMeterModelByMeterNumber(Long meterNumber);
    void deleteByMeterNumber(Long meterNumber);
}
