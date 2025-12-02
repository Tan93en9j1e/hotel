// ItemRepository.java
package com.example.hotel.repository;
import com.example.hotel.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByCode(String code);
}