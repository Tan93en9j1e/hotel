// src/main/java/com/example/hotel/service/ExecutiveReportService.java
package com.example.hotel.service;

import com.example.hotel.model.*;
import com.example.hotel.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExecutiveReportService {

    @Autowired private GuestRepository guestRepo;
    @Autowired private BillItemRepository billItemRepo;
    @Autowired private EmployeeRepository empRepo;
    @Autowired private RoomRepository roomRepo;
    @Autowired private KtvRoomRepository ktvRoomRepo;
    @Autowired private DiningTableRepository diningTableRepo;

    // 1. 客人年龄分布（分段）
    public Map<String, Long> getGuestAgeDistribution() {
        List<Guest> guests = guestRepo.findAll();
        Map<String, Long> ageGroups = new LinkedHashMap<>();
        ageGroups.put("18岁以下", 0L);
        ageGroups.put("18-30岁", 0L);
        ageGroups.put("31-45岁", 0L);
        ageGroups.put("46-60岁", 0L);
        ageGroups.put("60岁以上", 0L);

        for (Guest g : guests) {
            int age = LocalDate.now().getYear() - g.getCheckInDate().getYear(); // 简化计算
            if (age < 18) ageGroups.merge("18岁以下", 1L, Long::sum);
            else if (age <= 30) ageGroups.merge("18-30岁", 1L, Long::sum);
            else if (age <= 45) ageGroups.merge("31-45岁", 1L, Long::sum);
            else if (age <= 60) ageGroups.merge("46-60岁", 1L, Long::sum);
            else ageGroups.merge("60岁以上", 1L, Long::sum);
        }
        return ageGroups;
    }

    // 2. 收入构成（按账单类别）
    public Map<String, BigDecimal> getRevenueBreakdown() {
        List<BillItem> items = billItemRepo.findAll();
        return items.stream()
                .collect(Collectors.groupingBy(
                        BillItem::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, BillItem::getAmount, BigDecimal::add)
                ));
    }

    // 3. 员工与房间配置比
    public String getEmployeeToRoomRatio() {
        long empCount = empRepo.count();
        long roomCount = roomRepo.count();
        double ratio = roomCount > 0 ? (double) empCount / roomCount : 0;
        return String.format("%.2f 人/房", ratio);
    }

    // 4. 房间入住率（简化：当前有客人登记即视为占用）
    public String getRoomOccupancyRate() {
        long totalRooms = roomRepo.count();
        long occupied = guestRepo.findAll().stream()
                .map(Guest::getRoomNumber)
                .filter(Objects::nonNull)
                .distinct()
                .count();
        double rate = totalRooms > 0 ? (double) occupied / totalRooms * 100 : 0;
        return String.format("%.1f%%", rate);
    }
}