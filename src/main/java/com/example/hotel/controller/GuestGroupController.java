// com.example.hotel.controller.GuestGroupController.java
package com.example.hotel.controller;

import com.example.hotel.model.Guest;
import com.example.hotel.model.GuestGroup;
import com.example.hotel.service.GuestGroupService;
import com.example.hotel.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/groups")
public class GuestGroupController {

    @Autowired
    private GuestGroupService groupService;

    @Autowired
    private GuestService guestService;

    // 团体列表
    @GetMapping
    public String listGroups(Model model) {
        model.addAttribute("groups", groupService.getAllGroups());
        return "group/group-list";
    }

    // 显示新建团体表单（带成员输入区）
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("group", new GuestGroup());
        // 初始化 5 个空成员（前端可动态增减）
        List<Guest> members = new ArrayList<>();
        for (int i = 0; i < 5; i++) members.add(new Guest());
        model.addAttribute("members", members);
        return "group/group-form";
    }

    // 保存团体 + 成员
    @PostMapping
    public String createGroup(@ModelAttribute GuestGroup group,
                              @RequestParam("memberName[]") List<String> names,
                              @RequestParam("memberGender[]") List<String> genders,
                              @RequestParam("memberHometown[]") List<String> hometowns,
                              @RequestParam(value = "memberWorkUnit[]", required = false) List<String> workUnits,
                              @RequestParam(value = "memberOccupation[]", required = false) List<String> occupations) {

        group.setCheckInDate(LocalDate.now());
        GuestGroup savedGroup = groupService.saveGroup(group);

        // 批量保存成员
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i) != null && !names.get(i).trim().isEmpty()) {
                Guest guest = new Guest();
                guest.setName(names.get(i));
                guest.setGender(genders.size() > i ? genders.get(i) : "");
                guest.setHometown(hometowns.size() > i ? hometowns.get(i) : "");
                guest.setWorkUnit(workUnits != null && workUnits.size() > i ? workUnits.get(i) : "");
                guest.setOccupation(occupations != null && occupations.size() > i ? occupations.get(i) : "");
                guest.setPurpose(group.getPurpose());
                guest.setCheckInDate(group.getCheckInDate());
                guest.setExpectedCheckOutDate(group.getExpectedCheckOutDate());
                guest.setGroupId(savedGroup.getId()); // 关联团体
                guestService.saveGuest(guest);
            }
        }

        return "redirect:/groups";
    }

    @GetMapping("/view/{id}")
    public String viewGroup(@PathVariable Long id, Model model) {
        Optional<GuestGroup> groupOpt = groupService.getGroupById(id);
        if (groupOpt.isPresent()) {
            GuestGroup group = groupOpt.get();
            model.addAttribute("group", group);
            model.addAttribute("members", guestService.getGuestsByGroupId(id));
            return "group/group-view";
        }
        return "redirect:/groups";
    }

}