package com.example.electricity_management_system.service;

import com.example.electricity_management_system.email.EmailService;
import com.example.electricity_management_system.model.MeterModel;
import com.example.electricity_management_system.model.NotificationModel;
import com.example.electricity_management_system.model.UserModel;
import com.example.electricity_management_system.repository.MeterRepository;
import com.example.electricity_management_system.repository.NotificationRepository;
import com.example.electricity_management_system.utils.NotificationStatus;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class NotificationService {
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final MeterRepository meterRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               EmailService emailService,
                               MeterRepository meterRepository) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
        this.meterRepository = meterRepository;
    }

    public List<NotificationModel> getNotification() {
        return notificationRepository.findAll();
    }
@Transactional
    @Scheduled(cron = "0 */5 * * * * ")
    public String runTokenExpiryCheck() {
        notificationRepository.checkExpiringTokens();
        return "Token Expiry Check has been run";
    }

    public String findNonSentNotificationAndSendNotification() {
//        this.runTokenExpiryCheck();
        List<NotificationModel> notifications = notificationRepository.findByStatus(NotificationStatus.UNREAD);
        int sentCount = 0;

        for (NotificationModel notification : notifications) {
            try {
                MeterModel meterNumber = notification.getMeter();

                if (meterNumber.id !=null) {
                    UserModel user = meterNumber.user;

                    if (user != null && user.getEmail() != null) {
                        emailService.sendNotification(
                                user.getEmail(),
                                notification.getMessage()
                        );
                        notification.setStatus(NotificationStatus.SENT_TO_EMAIL);
                        notificationRepository.save(notification);
                        sentCount++;
                    } else {
                        log.warn("User or email not found for meter number: {}", notification.getMeter().meterNumber);
                    }
                } else {
                    log.warn("Meter not found for number: {}", notification.getMeter().meterNumber);
                }
            } catch (Exception e) {
                log.error("Failed to send notification ID: " + notification.getId(), e);
            }
        }

        return String.format("Processed %d notifications, successfully sent %d emails",
                notifications.size(), sentCount);
    }
}