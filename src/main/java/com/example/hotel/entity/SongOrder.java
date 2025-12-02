package com.example.hotel.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

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

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getKtvRoomNumber() { return ktvRoomNumber; }
    public void setKtvRoomNumber(String ktvRoomNumber) { this.ktvRoomNumber = ktvRoomNumber; }

    public String getSongName() { return songName; }
    public void setSongName(String songName) { this.songName = songName; }

    public String getSinger() { return singer; }
    public void setSinger(String singer) { this.singer = singer; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Boolean getBilled() { return billed != null ? billed : false; }
    public void setBilled(Boolean billed) { this.billed = billed; }
}