package com.example.electricity_management_system.model;

import com.example.electricity_management_system.utils.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Table(name="notifications")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String message;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime issuedDate=LocalDateTime.now();
    public NotificationStatus status=NotificationStatus.UNREAD;
    @OneToOne
    public MeterModel meter;
    @ManyToOne
    public UserModel user;
}
