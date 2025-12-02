package com.example.hotel.model;// src/main/java/com/example/hotel/accounting/model/Voucher.java

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Voucher {
    // getters & setters
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String summary;       // 摘要

    @ManyToOne
    private Account debitAccount; // 借方科目
    private BigDecimal debitAmount;

    @ManyToOne
    private Account creditAccount; // 贷方科目
    private BigDecimal creditAmount;

}