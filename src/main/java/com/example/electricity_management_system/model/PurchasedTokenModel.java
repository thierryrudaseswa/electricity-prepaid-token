package com.example.electricity_management_system.model;
import com.example.electricity_management_system.dto.PurchasedTokenRegistrationDto;
import com.example.electricity_management_system.utils.TokenFormatter;
import com.example.electricity_management_system.utils.TokenStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="purchased_tokens")
public class PurchasedTokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    public Long id;
    @Column(nullable = false,length = 16)
    @JsonIgnore
    public String token;
    @Column(length = 11)
    public Long tokenValueDays;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    public LocalDateTime purchaseDate=LocalDateTime.now();
    @Column(length = 11)
    public Integer amount;
    public TokenStatus tokenStatus=TokenStatus.NEW;
    @ManyToOne()
    @JoinColumn(name = "meter_id")
    public MeterModel meterNumber;
    @JsonProperty("token")
    public String getFormattedPurchaseDate() {
        return TokenFormatter.formatToken(this.token);
    }









}
