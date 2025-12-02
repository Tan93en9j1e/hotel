package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "ktv_rooms")
public class KtvRoom {

    // Getters and Setters
    @Id
    private String roomNumber; // 如 "K01", "VIP-K"

    private String status; // "空闲" / "使用中"

    public KtvRoom() {}

    public KtvRoom(String roomNumber) {
        this.roomNumber = roomNumber;
        this.status = "空闲";
    }

}