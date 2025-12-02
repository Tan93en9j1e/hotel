// src/main/java/com/example/hotel/model/Bill.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String guestName;
    private String roomNumber;
    private String billType; // "individual", "group"
    private BigDecimal advancePayment = BigDecimal.ZERO; // 预付款
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal balance; // balance = total - advance
    private boolean settled = false;
    private LocalDateTime createTime = LocalDateTime.now();

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> items = new ArrayList<>();
}