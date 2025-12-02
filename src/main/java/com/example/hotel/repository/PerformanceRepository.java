package com.example.hotel.repository;

import com.example.hotel.model.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    Performance findByEmployeeIdAndPeriod(Long empId, YearMonth period);
}