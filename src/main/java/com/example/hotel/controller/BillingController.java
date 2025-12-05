package com.example.hotel.controller;

import com.example.hotel.model.Bill;
import com.example.hotel.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

// src/main/java/com/example/hotel/controller/BillingController.java
@Controller
@RequestMapping("/billing")
public class BillingController {
    @Autowired
    private BillingService billingService;

    @GetMapping("/new")
    public String newBillForm() {
        return "billing/bill-form";
    }

    @PostMapping("/create")
    public String createBill(@RequestParam String guestName,
                             @RequestParam String roomNumber,
                             @RequestParam String billType) {
        Bill bill = billingService.createOrUpdateBill(guestName, roomNumber, billType);
        return "redirect:/billing/" + bill.getId();
    }

    @GetMapping("/{id}")
    public String billDetail(@PathVariable Long id, Model model) {
        Bill bill = billingService.getUnsettledBills().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("账单不存在或已结算"));
        model.addAttribute("bill", bill);
        return "billing/bill-detail";
    }

    @PostMapping("/item")
    public String addItem(@RequestParam Long billId,
                          @RequestParam String description,
                          @RequestParam BigDecimal amount,
                          @RequestParam String category) {
        billingService.addItem(billId, description, amount, category);
        return "redirect:/billing/" + billId;
    }

    @PostMapping("/advance")
    public String addAdvance(@RequestParam Long billId,
                             @RequestParam BigDecimal payment) {
        billingService.addAdvancePayment(billId, payment);
        return "redirect:/billing/" + billId;
    }

    @PostMapping("/settle")
    public String settle(@RequestParam Long billId) {
        billingService.settleBill(billId);
        return "redirect:/billing/receipt/" + billId;
    }

    @GetMapping("/receipt/{id}")
    public String receipt(@PathVariable Long id, Model model) {
        Bill bill = billingService.getUnsettledBills().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst().orElse(null);
        model.addAttribute("bill", bill);
        return "billing/receipt";
    }
}