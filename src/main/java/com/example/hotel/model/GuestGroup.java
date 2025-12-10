package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "guest_groups")
public class GuestGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;           // 团体名称（如“XX公司考察团”）
    private String contactPerson;       // 联系人
    private String contactPhone;        // 联系电话
    private Integer memberCount;        // 成员人数（可选，用于校验）
    private String purpose;             // 团体住店事由
    private LocalDate checkInDate;      // 入住日期
    private LocalDate expectedCheckOutDate; // 预定离店日期

    // 可选：主房间号（如团长房间）
    private String mainRoomNumber;

    // 注：不直接维护 Guest 列表以避免级联复杂性，通过 Guest.groupId 关联
}
