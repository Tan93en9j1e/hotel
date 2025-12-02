// src/main/java/com/example/hotel/service/PayrollService.java
package com.example.hotel.service;

import com.example.hotel.model.*;
import com.example.hotel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class PayrollService {

    @Autowired private EmployeeRepository empRepo;
    @Autowired private AttendanceRepository attRepo;
    @Autowired private PerformanceRepository perfRepo;
    @Autowired private SalaryRecordRepository salRepo;

    public SalaryRecord calculateSalary(Long empId, YearMonth period) {
        Employee emp = empRepo.findById(empId).orElseThrow();
        double base = emp.getBaseSalary();

        // 绩效奖金：绩效分 * 10 元
        Performance perf = perfRepo.findByEmployeeIdAndPeriod(empId, period);
        double bonus = (perf != null) ? perf.getScore() * 10 : 0;

        // 扣款：迟到 10元/分钟，缺勤 200元/天
        LocalDate start = period.atDay(1);
        LocalDate end = period.atEndOfMonth();
        List<Attendance> attendances = attRepo.findByEmployeeIdAndDateBetween(empId, start, end);
        double deduction = 0;
        for (Attendance a : attendances) {
            if (a.isAbsent()) deduction += 200;
            else deduction += a.getLateMinutes() * 10;
        }

        double total = base + bonus - deduction;
        if (total < 0) total = 0;

        SalaryRecord record = new SalaryRecord();
        record.setEmployee(emp);
        record.setPeriod(period);
        record.setBaseSalary(BigDecimal.valueOf(base));
        record.setPerformanceBonus(BigDecimal.valueOf(bonus));
        record.setDeduction(BigDecimal.valueOf(deduction));
        record.setTotalSalary(BigDecimal.valueOf(total));

        return salRepo.save(record);
    }

    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return empRepo.findById(id).orElse(null);
    }
}