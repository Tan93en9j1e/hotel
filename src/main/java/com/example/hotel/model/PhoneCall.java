// src/main/java/com/example/hotel/model/PhoneCall.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "phone_calls")
public class PhoneCall {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guestName;       // 客人姓名
    private String roomNumber;      // 房间号
    private String phoneNumber;     // 拨打号码
    private LocalDateTime startTime;
    private int durationMinutes;    // 通话时长（分钟）
    private BigDecimal cost;        // 费用（元）
    private boolean settled = false; // 是否已结账

}