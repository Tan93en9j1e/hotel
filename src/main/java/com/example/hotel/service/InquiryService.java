// src/main/java/com/example/hotel/service/InquiryService.java
package com.example.hotel.service;

import com.example.hotel.model.ServiceGuideItem;
import com.example.hotel.repository.ServiceGuideItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class InquiryService {

    @Autowired
    private ServiceGuideItemRepository guideItemRepository;

    private static final Map<String, String> CATEGORY_LABELS = Map.of(
            "location", "地理位置",
            "layout", "楼层布局",
            "room", "客房实景",
            "dining", "餐厅实景",
            "ktv", "KTV包房",
            "meeting", "会议室",
            "dish", "特色菜肴"
    );

    public List<String> getCategories() {
        return Arrays.asList("location", "layout", "room", "dining", "ktv", "meeting", "dish");
    }

    public String getCategoryLabel(String category) {
        return CATEGORY_LABELS.getOrDefault(category, category);
    }

    public List<ServiceGuideItem> getItemsByCategory(String category) {
        return guideItemRepository.findByCategoryOrderByTitle(category);
    }

    public ServiceGuideItem getItemById(Long id) {
        return guideItemRepository.findById(id).orElse(null);
    }
}