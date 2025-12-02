package com.example.hotel.controller;

import com.example.hotel.model.Employee;
import com.example.hotel.repository.EmployeeRepository;
import com.example.hotel.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

// src/main/java/com/example/hotel/controller/PayrollController.java
@Controller
@RequestMapping("/payroll")
public class PayrollController {
    @Autowired
    private PayrollService payrollService;
    @Autowired private EmployeeRepository empRepo;
    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", payrollService.getAllEmployees());
        return "payroll/employee-list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("employee", payrollService.getEmployeeById(id));
        return "payroll/employee-detail";
    }

    @PostMapping("/calculate")
    public String calculate(@RequestParam Long empId,
                            @RequestParam String yearMonth) {
        YearMonth period = YearMonth.parse(yearMonth);
        payrollService.calculateSalary(empId, period);
        return "redirect:/payroll/" + empId;
    }
    @GetMapping("/new")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "payroll/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {
        // 简单校验
        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("姓名不能为空");
        }
        empRepo.save(employee);
        return "redirect:/payroll";
    }
}