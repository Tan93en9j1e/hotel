package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;          // 科目编码，如 1001, 100201
    private String name;          // 科目名称
    @Enumerated(EnumType.STRING)
    private AccountCategory category;
    private boolean detail;       // 是否明细科目（true=二级）

    // 用于快速查询余额（可选，也可实时计算）
    private BigDecimal balance = BigDecimal.ZERO;
}
