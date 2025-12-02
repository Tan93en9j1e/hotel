package com.example.hotel.service;

import com.example.hotel.model.KtvRoom;
import com.example.hotel.model.SongOrder;
import com.example.hotel.repository.KtvRoomRepository;
import com.example.hotel.repository.SongOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class KtvService {

    @Autowired
    private KtvRoomRepository roomRepo;

    @Autowired
    private SongOrderRepository songRepo;

    public List<KtvRoom> getAllRooms() {
        return roomRepo.findAll();
    }

    public KtvRoom saveRoom(KtvRoom room) {
        return roomRepo.save(room);
    }

    public void deleteRoom(String number) {
        roomRepo.deleteById(number);
    }

    public List<SongOrder> getUnbilledSongs(String roomNumber) {
        return songRepo.findByKtvRoomNumberAndBilled(roomNumber, false);
    }

    public BigDecimal calculateTotal(String roomNumber) {
        return getUnbilledSongs(roomNumber).stream()
                .map(SongOrder::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addSongOrder(SongOrder order) {
        KtvRoom room = roomRepo.findById(order.getKtvRoomNumber())
                .orElse(new KtvRoom(order.getKtvRoomNumber()));
        room.setStatus("使用中");
        roomRepo.save(room);
        order.setBilled(false);
        songRepo.save(order);
    }

    public void settleBill(String roomNumber) {
        songRepo.markAllAsBilledByRoom(roomNumber);
        KtvRoom room = roomRepo.findById(roomNumber).orElse(null);
        if (room != null) {
            room.setStatus("空闲");
            roomRepo.save(room);
        }
    }
}