// src/main/java/com/example/hotel/model/ServiceGuideItem.java
package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "service_guide_items")
public class ServiceGuideItem {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;           // 标题，如“餐厅布局”
    private String category;        // 分类：location, layout, room, dining, ktv, meeting, dish
    private String description;     // 描述
    private String imageUrl;        // 图片路径（如 /images/guide/dining1.jpg）
    private String contact;
}