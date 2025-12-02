// src/main/java/com/example/hotel/model/BillItem.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Table(name = "bill_items")
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    private String description; // 如“房费”、“电话费”、“洗衣费”
    private BigDecimal amount;
    private String category; // room, phone, dining, laundry, other


}