package com.example.electricity_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity

public class MeterModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(unique = true, nullable = false,length = 6)
    public Long meterNumber;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    public UserModel user;
    @JsonIgnore
    @OneToMany(mappedBy = "meterNumber",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    public List<PurchasedTokenModel> purchasedTokens;
}
