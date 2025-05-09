    package com.example.electricity_management_system.repository;

    import com.example.electricity_management_system.model.MeterModel;
    import com.example.electricity_management_system.model.PurchasedTokenModel;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;
    import java.util.Optional;

    @Repository
    public interface PurchasedTokenRepository extends JpaRepository<PurchasedTokenModel,Long> {
        boolean existsByToken(String string);
        Optional<PurchasedTokenModel> findByToken(String token);

        List<PurchasedTokenModel> findByMeterNumber(MeterModel meterNumber);

    }
