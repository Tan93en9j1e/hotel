package com.example.hotel.controller;

import com.example.hotel.entity.Guest;
import com.example.hotel.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping
    public String listGuests(Model model) {
        List<Guest> guests = guestService.getAllGuests();
        model.addAttribute("guests", guests);
        return "guest/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("guest", new Guest());
        return "guest/form";
    }

    @PostMapping
    public String createGuest(@ModelAttribute Guest guest) {
        guest.setCheckInDate(LocalDate.now());
        guestService.saveGuest(guest);
        return "redirect:/guests";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        guestService.getGuestById(id).ifPresent(g -> model.addAttribute("guest", g));
        return "guest/form";
    }

    @PostMapping("/update/{id}")
    public String updateGuest(@PathVariable Long id, @ModelAttribute Guest guest) {
        guest.setId(id);
        guestService.saveGuest(guest);
        return "redirect:/guests";
    }

    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return "redirect:/guests";
    }
}