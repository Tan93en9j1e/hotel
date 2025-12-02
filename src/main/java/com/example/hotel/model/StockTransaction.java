// src/main/java/com/example/hotel/inventory/model/StockTransaction.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class StockTransaction {
    // getters & setters
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    private String type; // "IN" or "OUT"
    private Integer quantity;
    private BigDecimal amount; // quantity * item.price

    @ManyToOne
    private Item item;

    private String remark;

}