package com.example.hotel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    private String roomNumber; // 如 "101", "205"

    private String status; // "空闲" / "占用"

    @Column(length = 1000)
    private String items; // 物品种类及状态，如 "电视:正常,空调:故障,床:干净"

    private String currentGuestName;
    private String guestMessage; // 客人留言

    // Constructors
    public Room() {}

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
        this.status = "空闲";
    }

    // Getters and Setters
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }

    public String getCurrentGuestName() { return currentGuestName; }
    public void setCurrentGuestName(String currentGuestName) { this.currentGuestName = currentGuestName; }

    public String getGuestMessage() { return guestMessage; }
    public void setGuestMessage(String guestMessage) { this.guestMessage = guestMessage; }
}