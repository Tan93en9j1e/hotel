package com.example.hotel.controller;

import com.example.hotel.entity.KtvRoom;
import com.example.hotel.entity.SongOrder;
import com.example.hotel.service.KtvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/ktv")
public class KtvController {

    @Autowired
    private KtvService ktvService;

    // 包房管理
    @GetMapping("/rooms")
    public String listRooms(Model model) {
        model.addAttribute("rooms", ktvService.getAllRooms());
        return "ktv/room-list";
    }

    @GetMapping("/rooms/new")
    public String newRoomForm(Model model) {
        model.addAttribute("room", new KtvRoom());
        return "ktv/room-form";
    }

    @PostMapping("/rooms")
    public String createRoom(@ModelAttribute KtvRoom room) {
        ktvService.saveRoom(room);
        return "redirect:/ktv/rooms";
    }

    @GetMapping("/rooms/edit/{number}")
    public String editRoomForm(@PathVariable String number, Model model) {
        model.addAttribute("room", ktvService.getAllRooms().stream()
                .filter(r -> r.getRoomNumber().equals(number)).findFirst().orElse(new KtvRoom()));
        return "ktv/room-form";
    }

    @PostMapping("/rooms/update/{number}")
    public String updateRoom(@PathVariable String number, @ModelAttribute KtvRoom room) {
        room.setRoomNumber(number);
        ktvService.saveRoom(room);
        return "redirect:/ktv/rooms";
    }

    @GetMapping("/rooms/delete/{number}")
    public String deleteRoom(@PathVariable String number) {
        ktvService.deleteRoom(number);
        return "redirect:/ktv/rooms";
    }

    // 点歌
    @GetMapping("/song/{roomNumber}")
    public String songForm(@PathVariable String roomNumber, Model model) {
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("songOrder", new SongOrder());
        return "ktv/song-form";
    }

    @PostMapping("/song")
    public String placeSongOrder(@ModelAttribute SongOrder order) {
        ktvService.addSongOrder(order);
        return "redirect:/ktv/bill/" + order.getKtvRoomNumber();
    }

    // 查看账单 & 结账
    @GetMapping("/bill/{roomNumber}")
    public String viewBill(@PathVariable String roomNumber, Model model) {
        List<SongOrder> songs = ktvService.getUnbilledSongs(roomNumber);
        BigDecimal total = ktvService.calculateTotal(roomNumber);
        model.addAttribute("roomNumber", roomNumber);
        model.addAttribute("songs", songs);
        model.addAttribute("total", total);
        return "ktv/bill";
    }

    @GetMapping("/settle/{roomNumber}")
    public String settleBill(@PathVariable String roomNumber) {
        ktvService.settleBill(roomNumber);
        return "redirect:/ktv/rooms";
    }
}