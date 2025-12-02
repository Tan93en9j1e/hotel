// src/main/java/com/example/hotel/model/Performance.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Setter
@Getter
@Entity
@Table(name = "performances")
public class Performance {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private YearMonth period;     // 如 2025-11
    private double score;         // 绩效分（0~100）
    private String comment;

}