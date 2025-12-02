// src/main/java/com/example/hotel/model/Performance.java
package com.example.hotel.model;

import jakarta.persistence.*;
import java.time.YearMonth;

@Entity
@Table(name = "performances")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private YearMonth period;     // 如 2025-11
    private double score;         // 绩效分（0~100）
    private String comment;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public YearMonth getPeriod() { return period; }
    public void setPeriod(YearMonth period) { this.period = period; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}