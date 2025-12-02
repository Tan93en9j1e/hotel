// src/main/java/com/example/hotel/inventory/model/Item.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
public class Item {
    // getters & setters
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;      // 物品编码
    private String name;      // 名称
    private String unit;      // 单位：件、箱、千克
    private BigDecimal price; // 单价
    private Integer stockQty = 0; // 当前库存数量

}