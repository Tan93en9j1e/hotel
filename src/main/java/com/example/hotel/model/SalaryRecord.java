// src/main/java/com/example/hotel/model/SalaryRecord.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Setter
@Getter
@Entity
@Table(name = "salary_records")
public class SalaryRecord {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private YearMonth period;
    private BigDecimal baseSalary;
    private BigDecimal performanceBonus;
    private BigDecimal deduction; // 扣款（迟到、缺勤）
    private BigDecimal totalSalary;
    private boolean paid = false;

}