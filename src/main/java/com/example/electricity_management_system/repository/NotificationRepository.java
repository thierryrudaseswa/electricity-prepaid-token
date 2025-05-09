package com.example.electricity_management_system.repository;

import com.example.electricity_management_system.model.NotificationModel;
import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.utils.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel,Long> {
    List<NotificationModel> findByUser(UserModel user);
    @Modifying
    @Query(value = "CALL check_expiring_tokens()", nativeQuery = true)
    void checkExpiringTokens();
    List<NotificationModel> findByStatus(NotificationStatus status);
}
