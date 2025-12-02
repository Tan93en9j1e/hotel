// src/main/java/com/example/hotel/repository/ServiceGuideItemRepository.java
package com.example.hotel.repository;

import com.example.hotel.entity.ServiceGuideItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceGuideItemRepository extends JpaRepository<ServiceGuideItem, Long> {
    List<ServiceGuideItem> findByCategoryOrderByTitle(String category);
}