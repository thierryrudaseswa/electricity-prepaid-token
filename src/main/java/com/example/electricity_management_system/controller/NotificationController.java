package com.example.electricity_management_system.controller;

import com.example.electricity_management_system.dto.SuccessResponse;
import com.example.electricity_management_system.model.NotificationModel;
import com.example.electricity_management_system.service.NotificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@Tag(name = "Notification")
public class NotificationController {
    private NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PostMapping("/all")
    public ResponseEntity<SuccessResponse<List<NotificationModel>>> getNotifications(){
        List<NotificationModel> notifications=notificationService.getNotification();
        return ResponseEntity.ok().body(new SuccessResponse<>("Notifications Retrieved Successfully",notifications));
    }
    @PostMapping("/send")
    public ResponseEntity<SuccessResponse<String>> sendNotificationToEmail(){

        String message=notificationService.findNonSentNotificationAndSendNotification();
        return ResponseEntity.ok().body(new SuccessResponse<>(message));
    }

}
