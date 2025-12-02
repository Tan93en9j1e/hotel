package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "rooms")
public class Room {

    // Getters and Setters
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

}