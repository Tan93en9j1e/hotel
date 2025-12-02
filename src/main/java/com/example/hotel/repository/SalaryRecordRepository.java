package com.example.hotel.repository;

import com.example.hotel.model.SalaryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;

public interface SalaryRecordRepository extends JpaRepository<SalaryRecord, Long> {
    SalaryRecord findByEmployeeIdAndPeriod(Long empId, YearMonth period);
}