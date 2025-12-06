package com.example.hotel.controller;

import com.example.hotel.model.Room;
import com.example.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String listRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "room-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room());
        return "room-form";
    }

    @PostMapping
    public String createRoom(@ModelAttribute Room room) {
        if (roomService.getRoomByNumber(room.getRoomNumber()).isPresent()) {
            // 房间已存在，可返回错误或忽略
            return "redirect:/rooms";
        }

        // 根据是否有客人自动设置状态
        if (room.getCurrentGuestName() != null && !room.getCurrentGuestName().trim().isEmpty()) {
            room.setStatus("占用");
        } else {
            room.setStatus("空闲");
            room.setCurrentGuestName(null); // 清空更干净
        }

        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{number}")
    public String showEditForm(@PathVariable String number, Model model) {
        roomService.getRoomByNumber(number).ifPresent(r -> model.addAttribute("room", r));
        return "room-form";
    }

    @PostMapping("/update/{number}")
    public String updateRoom(@PathVariable String number, @ModelAttribute Room room) {
        room.setRoomNumber(number);
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    @GetMapping("/delete/{number}")
    public String deleteRoom(@PathVariable String number) {
        roomService.deleteRoom(number);
        return "redirect:/rooms";
    }

}