// File: src/main/java/com/example/hotel/model/Guest.java

package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // 姓名设为非空
    private String name;

    private String gender; // 男 / 女

    private String hometown;

    @Column(name = "work_unit")
    private String workUnit;

    private String occupation;

    @Column(name = "purpose")
    private String purpose; // 住店事由

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "expected_check_out_date")
    private LocalDate expectedCheckOutDate;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(length = 1000, name = "room_change_records")
    private String roomChangeRecords; // 换房记录，用字符串存储

    // Constructors
    public Guest() {}

    /**
     * 计算属性：判断客人当前是否仍入住
     * @return true if still checked in, false otherwise
     */
    @Transient // 不映射到数据库
    public boolean isStillCheckedIn() {
        if (this.expectedCheckOutDate == null) {
            return true; // 如果没有预定离店日期，默认认为在住
        }
        return !LocalDate.now().isAfter(this.expectedCheckOutDate);
    }

    /**
     * 获取入住状态描述
     * @return "在住" or "已离店"
     */
    @Transient
    public String getStatusDescription() {
        return isStillCheckedIn() ? "在住" : "已离店";
    }
    // 关联房间（外键在 guests 表中）
    @OneToOne
    @JoinColumn(name = "room_number") // 外键列名
    private Room room;

}