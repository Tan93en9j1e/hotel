// src/main/java/com/example/hotel/model/Employee.java
package com.example.hotel.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender; // "男" / "女"
    private int age;
    private String education; // 学历：高中、大专、本科等
    private String position;  // 职务：前台、厨师、经理等
    private double baseSalary; // 基本工资
    private boolean active = true;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public double getBaseSalary() { return baseSalary; }
    public void setBaseSalary(double baseSalary) { this.baseSalary = baseSalary; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}