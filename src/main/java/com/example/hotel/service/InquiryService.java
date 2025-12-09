// src/main/java/com/example/hotel/service/InquiryService.java
package com.example.hotel.service;

import com.example.hotel.model.ServiceCategory;
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



    public List<String> getCategories() {
        return ServiceCategory.getAllCodes();
    }

    public String getCategoryLabel(String category) {
        return ServiceCategory.getLabelByCode(category);
    }

    public List<ServiceGuideItem> getItemsByCategory(String category) {
        if (!ServiceCategory.getAllCodes().contains(category)) {
            throw new IllegalArgumentException("无效的服务指南分类: " + category);
        }
        return guideItemRepository.findByCategoryOrderByTitle(category);
    }

    public ServiceGuideItem getItemById(Long id) {
        return guideItemRepository.findById(id).orElse(null);
    }
}