// File: src/main/java/com/example/hotel/controller/GuestController.java

package com.example.hotel.controller;

import com.example.hotel.model.Guest;
import com.example.hotel.model.Room;
import com.example.hotel.service.GuestService;
import com.example.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;
    @Autowired
    private RoomService roomService;

    @GetMapping
    public String listGuests(
            @RequestParam(value = "search", required = false) String searchQuery,
            Model model) {

        List<Guest> guests;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            // 简单搜索：同时匹配姓名和房间号
            guests = guestService.searchGuests(searchQuery);
        } else {
            guests = guestService.getAllGuests();
        }

        model.addAttribute("guests", guests);
        model.addAttribute("searchQuery", searchQuery); // 回传搜索词以便回显
        return "guest-list";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("guest", new Guest());
        model.addAttribute("title", "新增客人"); // 传递标题
        model.addAttribute("action", "/guests"); // 传递提交地址

        // 获取所有空闲房间
        List<Room> availableRooms = roomService.getAllRooms().stream()
                .filter(room -> "空闲".equals(room.getStatus()))
                .collect(Collectors.toList());
        model.addAttribute("availableRooms", availableRooms);

        return "guest-form"; // 返回视图名称
    }

    @PostMapping
    public String createGuest(@ModelAttribute Guest guest) {
        guest.setCheckInDate(LocalDate.now()); // 自动设置入住日期为今天
        guestService.saveGuest(guest);
        return "redirect:/guests";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Guest> guestOpt = guestService.getGuestById(id);
        if (guestOpt.isPresent()) {
            model.addAttribute("guest", guestOpt.get());
            model.addAttribute("title", "编辑客人信息"); // 传递标题
            model.addAttribute("action", "/guests/" + id); // 传递提交地址

            // 获取所有空闲房间 + 当前客人已占用的房间（如果仍在占用）
            List<Room> allRooms = roomService.getAllRooms();
            List<Room> availableRooms = allRooms.stream()
                    .filter(room -> "空闲".equals(room.getStatus()))
                    .collect(Collectors.toList());

            Guest guest = guestOpt.get();
            // 如果客人当前有房间号，并且该房间存在，则将其加入可用列表（允许用户不换房或换回原房）
            if (guest.getRoomNumber() != null && !guest.getRoomNumber().isEmpty()) {
                boolean isCurrentRoomInList = availableRooms.stream()
                        .anyMatch(r -> guest.getRoomNumber().equals(r.getRoomNumber()));
                if (!isCurrentRoomInList) {
                    Optional<Room> currentRoomOpt = allRooms.stream()
                            .filter(r -> guest.getRoomNumber().equals(r.getRoomNumber()))
                            .findFirst();
                    if (currentRoomOpt.isPresent()) {
                        // 即使不是空闲，也暂时加入列表以便编辑时选择，防止丢失原有值
                        availableRooms.add(currentRoomOpt.get());
                    }
                }
            }

            model.addAttribute("availableRooms", availableRooms);

            return "guest-form";
        } else {
            // 如果找不到客人，重定向到列表页或其他错误处理
            return "redirect:/guests";
        }
    }

    @PostMapping("/update/{id}")
    public String updateGuest(@PathVariable Long id, @ModelAttribute Guest guest) {
        guest.setId(id); // 确保ID不被表单覆盖
        guestService.saveGuest(guest);
        return "redirect:/guests";
    }

    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return "redirect:/guests";
    }
    @PostMapping("/guests")
    public String addGuest(@RequestParam String name,
                           @RequestParam String roomNumber) {

        Guest guest = new Guest();
        guest.setName(name);

        guestService.checkIn(guest, roomNumber);

        return "redirect:/guests";
    }
}