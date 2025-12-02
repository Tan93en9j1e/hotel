// src/main/java/com/example/hotel/controller/ExecutiveReportController.java
package com.example.hotel.controller;

import com.example.hotel.service.ExecutiveReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExecutiveReportController {

    @Autowired
    private ExecutiveReportService reportService;

    @GetMapping("/executive")
    public String dashboard(Model model) {
        model.addAttribute("guestAgeStats", reportService.getGuestAgeDistribution());
        model.addAttribute("revenueBreakdown", reportService.getRevenueBreakdown());
        model.addAttribute("employeeRoomRatio", reportService.getEmployeeToRoomRatio());
        model.addAttribute("roomOccupancy", reportService.getRoomOccupancyRate());
        return "executive/dashboard";
    }
}