package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Table(name = "song_orders")
public class SongOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ktvRoomNumber;
    private String songName;
    private String singer;
    private Integer durationMinutes; // 时长（分钟）
    private BigDecimal price;
    private Boolean billed; // 是否已入账

    public SongOrder() {}

}