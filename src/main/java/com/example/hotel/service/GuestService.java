// File: src/main/java/com/example/hotel/service/GuestService.java

package com.example.hotel.service;

import com.example.hotel.model.Guest;
import com.example.hotel.model.Room;
import com.example.hotel.repository.GuestRepository;
import com.example.hotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;
    @Autowired
    private RoomRepository roomRepository;

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getGuestById(Long id) {
        return guestRepository.findById(id);
    }

    public Guest saveGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    // 新增：根据姓名或房间号搜索
    public List<Guest> searchGuests(String query) {
        // 注意：JPA Repository 默认不支持跨字段模糊查询
        // 这里采用 Java Stream 进行内存过滤，适用于小数据集
        // 对于大数据集，建议使用数据库级别的全文搜索或 QueryDSL/JPA Criteria API

        List<Guest> allGuests = getAllGuests();
        return allGuests.stream()
                .filter(guest ->
                        (guest.getName() != null && guest.getName().toLowerCase().contains(query.toLowerCase())) ||
                                (guest.getRoomNumber() != null && guest.getRoomNumber().toLowerCase().contains(query.toLowerCase()))
                )
                .collect(Collectors.toList());
    }
    public void checkIn(Guest guest, String roomNumber) {
        // 1. 查房间
        Room room = roomRepository.findById(roomNumber)
                .orElseThrow(() -> new RuntimeException("房间不存在"));

        // 2. 检查是否空闲
        if (!"空闲".equals(room.getStatus())) {
            throw new RuntimeException("房间已被占用");
        }

        // 3. 设置关联
        guest.setRoom(room);          // Guest → Room
        room.setCurrentGuest(guest);  // Room → Guest（可选，但保持双向一致）

        // 4. 更新房间状态
        room.setStatus("占用");

        // 5. 保存（由于 cascade = ALL，保存 guest 也会保存 room）
        guestRepository.save(guest);
    }
}