package com.example.hotel.repository;
import com.example.hotel.model.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {
    List<StockTransaction> findByItem_IdOrderByTimeDesc(Long itemId);
}