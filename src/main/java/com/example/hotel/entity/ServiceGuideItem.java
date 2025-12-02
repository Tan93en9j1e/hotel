// src/main/java/com/example/hotel/model/ServiceGuideItem.java
package com.example.hotel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "service_guide_items")
public class ServiceGuideItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;           // 标题，如“餐厅布局”
    private String category;        // 分类：location, layout, room, dining, ktv, meeting, dish
    private String description;     // 描述
    private String imageUrl;        // 图片路径（如 /images/guide/dining1.jpg）

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}