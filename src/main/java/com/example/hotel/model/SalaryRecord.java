// src/main/java/com/example/hotel/model/SalaryRecord.java
package com.example.hotel.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Table(name = "salary_records")
public class SalaryRecord {
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

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public YearMonth getPeriod() { return period; }
    public void setPeriod(YearMonth period) { this.period = period; }
    public BigDecimal getBaseSalary() { return baseSalary; }
    public void setBaseSalary(BigDecimal baseSalary) { this.baseSalary = baseSalary; }
    public BigDecimal getPerformanceBonus() { return performanceBonus; }
    public void setPerformanceBonus(BigDecimal performanceBonus) { this.performanceBonus = performanceBonus; }
    public BigDecimal getDeduction() { return deduction; }
    public void setDeduction(BigDecimal deduction) { this.deduction = deduction; }
    public BigDecimal getTotalSalary() { return totalSalary; }
    public void setTotalSalary(BigDecimal totalSalary) { this.totalSalary = totalSalary; }
    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }
}