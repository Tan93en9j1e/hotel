// src/main/java/com/example/hotel/model/Employee.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
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

}