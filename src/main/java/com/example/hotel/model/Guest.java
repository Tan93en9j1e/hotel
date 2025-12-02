package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender; // 男 / 女
    private String hometown;
    private String workUnit;
    private String occupation;
    private String purpose; // 住店事由
    private LocalDate checkInDate;
    private LocalDate expectedCheckOutDate;
    private String roomNumber;

    @Column(length = 1000)
    private String roomChangeRecords; // 换房记录，用字符串存储，如 "101->202 (2025-12-01)"

    // Constructors
    public Guest() {}


}