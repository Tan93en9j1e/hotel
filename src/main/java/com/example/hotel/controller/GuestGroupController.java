// src/main/java/com/example/hotelmanagement/controller/GuestGroupController.java

package com.example.hotel.controller;

import com.example.hotel.dto.GroupForm;
import com.example.hotel.model.Guest;
import com.example.hotel.model.GuestGroup;
import com.example.hotel.service.GuestGroupService;
import com.example.hotel.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        List<GuestGroup> groups = groupService.getAllGroups();
        model.addAttribute("groups", groups);
        return "group/group-list";
    }

    // 显示新建表单
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("groupForm", new GroupForm());
        return "group/group-form";
    }

    // 保存团体 + 成员
    @PostMapping
    public String createGroup(@ModelAttribute("groupForm") GroupForm form) {
        GuestGroup group = form.getGroup();
        group.setCheckInDate(LocalDate.now());
        GuestGroup savedGroup = groupService.saveGroup(group);

        // 保存有效成员
        for (var member : form.getMembers()) {
            if (member.getName() != null && !member.getName().trim().isEmpty()) {
                member.setGroupId(savedGroup.getId());
                member.setCheckInDate(group.getCheckInDate());
                guestService.saveGuest(member);
            }
        }

        return "redirect:/groups";
    }

    // 查看团体详情（含成员）
    @GetMapping("/view/{id}")
    public String viewGroup(@PathVariable Long id, Model model) {
        GuestGroup group = groupService.getGroupById(id);
        List<Guest> members = guestService.getGuestsByGroupId(id);
        model.addAttribute("group", group);
        model.addAttribute("members", members);
        return "group/group-view";
    }

    // 删除团体
    @GetMapping("/delete/{id}")
    public String deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return "redirect:/groups";
    }
}