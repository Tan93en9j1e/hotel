package com.example.hotel.service;

import com.example.hotel.model.Guest;
import com.example.hotel.model.GuestGroup;
import com.example.hotel.model.Room;
import com.example.hotel.repository.GuestRepository;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository; // 新增依赖

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getGuestById(Long id) {
        return guestRepository.findById(id);
    }

    @Transactional // 确保客人和房间状态一致
    public Guest saveGuest(Guest guest) {
        Guest savedGuest = guestRepository.save(guest);

        // 如果客人指定了房间号，则更新房间状态
        if (guest.getRoomNumber() != null && !guest.getRoomNumber().trim().isEmpty()) {
            Optional<Room> roomOpt = roomRepository.findById(guest.getRoomNumber());
            if (roomOpt.isPresent()) {
                Room room = roomOpt.get();
                // 只有当房间是“空闲”时才允许分配（可选策略）
                if ("空闲".equals(room.getStatus())) {
                    room.setStatus("占用");
                    room.setCurrentGuestName(guest.getName());
                    roomRepository.save(room);
                }
                // 如果房间已是“占用”，可选择抛出异常或忽略（这里暂不处理冲突）
            }
            // 如果房间不存在，可选择记录日志或抛异常（根据业务需求）
        }

        return savedGuest;
    }

    @Transactional
    public void deleteGuest(Long id) {
        // 可选：删除客人时释放房间（进阶功能）
        Optional<Guest> guestOpt = guestRepository.findById(id);
        if (guestOpt.isPresent()) {
            Guest guest = guestOpt.get();
            String roomNumber = guest.getRoomNumber();
            if (roomNumber != null && !roomNumber.trim().isEmpty()) {
                Optional<Room> roomOpt = roomRepository.findById(roomNumber);
                if (roomOpt.isPresent()) {
                    Room room = roomOpt.get();
                    // 只有当房间当前客人就是该客人时才释放（避免误释放）
                    if (guest.getName().equals(room.getCurrentGuestName())) {
                        room.setStatus("空闲");
                        room.setCurrentGuestName(null);
                        roomRepository.save(room);
                    }
                }
            }
        }
        guestRepository.deleteById(id);
    }
    // GuestService.java
    @Transactional
    public void deleteGuestsByGroupId(Long groupId) {
        List<Guest> guests = guestRepository.findByGroupId(groupId);
        for (Guest guest : guests) {
            // 如果成员占用了房间，先释放房间（复用现有逻辑）
            String roomNumber = guest.getRoomNumber();
            if (roomNumber != null && !roomNumber.trim().isEmpty()) {
                Optional<Room> roomOpt = roomRepository.findById(roomNumber);
                if (roomOpt.isPresent()) {
                    Room room = roomOpt.get();
                    if (guest.getName().equals(room.getCurrentGuestName())) {
                        room.setStatus("空闲");
                        room.setCurrentGuestName(null);
                        roomRepository.save(room);
                    }
                }
            }
        }
        // 批量删除成员
        guestRepository.deleteByGroupId(groupId);
    }

    public List<Guest> getGuestsByGroupId(Long groupId) {
        return guestRepository.findByGroupId(groupId);
    }
}