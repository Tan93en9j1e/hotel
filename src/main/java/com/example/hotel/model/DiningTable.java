package com.example.hotel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dining_tables")
public class DiningTable {

    @Id
    private String tableNumber; // 如 "T01", "VIP2"

    private String status; // "空闲" / "占用"

    public DiningTable() {}

    public DiningTable(String tableNumber) {
        this.tableNumber = tableNumber;
        this.status = "空闲";
    }

    // Getters and Setters
    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}