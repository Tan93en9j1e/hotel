package com.example.hotel.service;

import com.example.hotel.model.GuestGroup;
import com.example.hotel.model.Room;
import com.example.hotel.repository.GuestGroupRepository;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuestGroupService {

    @Autowired
    private GuestGroupRepository groupRepository;

    @Autowired
    private RoomRepository roomRepository; // 新增依赖

    public List<GuestGroup> getAllGroups() {
        return groupRepository.findAll();
    }

    // GuestGroupService.java
    public GuestGroup getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("团体不存在，ID: " + id));
    }

    @Transactional
    public GuestGroup saveGroup(GuestGroup group) {
        // 不再处理任何房间逻辑 —— 房间由 Guest 成员管理
        return groupRepository.save(group);
    }

    // GuestGroupService.java
    @Autowired
    private GuestService guestService; // ← 添加这个依赖

    @Transactional
    public void deleteGroup(Long id) {
        // 先删除该团体下的所有成员（散客不受影响）
        guestService.deleteGuestsByGroupId(id);
        // 再删除团体本身
        groupRepository.deleteById(id);
    }
}