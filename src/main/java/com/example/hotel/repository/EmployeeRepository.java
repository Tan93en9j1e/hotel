package com.example.hotel.repository;

import com.example.hotel.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

// EmployeeRepository.java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}