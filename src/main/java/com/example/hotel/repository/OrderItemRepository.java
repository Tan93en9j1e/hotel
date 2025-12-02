package com.example.hotel.repository;

import com.example.hotel.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByTableNumberAndBilled(String tableNumber, Boolean billed);

    @Modifying
    @Query("UPDATE OrderItem o SET o.billed = true WHERE o.tableNumber = :tableNumber AND o.billed = false")
    void markAllAsBilledByTable(String tableNumber);
}