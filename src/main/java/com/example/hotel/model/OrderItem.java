package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tableNumber; // 关联 DiningTable.tableNumber
    private String itemName;    // 菜品或酒水名称
    private String type;        // "菜" 或 "酒"
    private BigDecimal price;
    private Integer quantity;
    private Boolean billed;     // 是否已入账（结账）

    // Constructors
    public OrderItem() {}


    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}