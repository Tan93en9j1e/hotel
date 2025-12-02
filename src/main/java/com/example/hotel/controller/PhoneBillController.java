// src/main/java/com/example/hotel/controller/PhoneBillController.java
package com.example.hotel.controller;

import com.example.hotel.model.PhoneCall;
import com.example.hotel.service.PhoneBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/phone")
public class PhoneBillController {

    @Autowired
    private PhoneBillService phoneBillService;

    @GetMapping("/calls")
    public String listCalls(@RequestParam String roomNumber, Model model) {
        List<PhoneCall> calls = phoneBillService.getAllCalls(roomNumber);
        BigDecimal total = calls.stream()
                .filter(c -> !c.isSettled())
                .map(PhoneCall::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("calls", calls);
        model.addAttribute("total", total);
        return "phone/call-list";
    }

    @GetMapping("/new")
    public String newCallForm(@RequestParam String roomNumber, Model model) {
        model.addAttribute("roomNumber", roomNumber);
        return "phone/new-call";
    }

    @PostMapping("/record")
    public String recordCall(@RequestParam String guestName,
                             @RequestParam String roomNumber,
                             @RequestParam String phoneNumber,
                             @RequestParam int durationMinutes) {
        phoneBillService.recordCall(guestName, roomNumber, phoneNumber, durationMinutes);
        return "redirect:/phone/calls?roomNumber=" + roomNumber;
    }

    @GetMapping("/receipt")
    public String printReceipt(@RequestParam String roomNumber, Model model) {
        List<PhoneCall> calls = phoneBillService.getAllCalls(roomNumber);
        BigDecimal total = calls.stream()
                .map(PhoneCall::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("calls", calls);
        model.addAttribute("total", total);
        return "phone/receipt";
    }

    @PostMapping("/settle")
    public String settle(@RequestParam String roomNumber) {
        phoneBillService.settleCalls(roomNumber);
        return "redirect:/phone/calls?roomNumber=" + roomNumber;
    }
}