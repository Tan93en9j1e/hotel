package com.example.hotel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ktv_rooms")
public class KtvRoom {

    @Id
    private String roomNumber; // 如 "K01", "VIP-K"

    private String status; // "空闲" / "使用中"

    public KtvRoom() {}

    public KtvRoom(String roomNumber) {
        this.roomNumber = roomNumber;
        this.status = "空闲";
    }

    // Getters and Setters
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}