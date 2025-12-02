package com.example.hotel.service;// InventoryService.java

import com.example.hotel.model.Item;
import com.example.hotel.model.StockTransaction;
import com.example.hotel.repository.ItemRepository;
import com.example.hotel.repository.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class InventoryService {

    @Autowired private ItemRepository itemRepo;
    @Autowired private StockTransactionRepository stockRepo;

    public void stockIn(String itemCode, int qty, String remark) {
        Item item = itemRepo.findByCode(itemCode);
        if (item == null) throw new RuntimeException("物品不存在");

        item.setStockQty(item.getStockQty() + qty);
        itemRepo.save(item);

        StockTransaction tx = new StockTransaction();
        tx.setItem(item);
        tx.setType("IN");
        tx.setQuantity(qty);
        tx.setAmount(item.getPrice().multiply(BigDecimal.valueOf(qty)));
        tx.setTime(LocalDateTime.now());
        tx.setRemark(remark);
        stockRepo.save(tx);
    }

    public void stockOut(String itemCode, int qty, String remark) {
        Item item = itemRepo.findByCode(itemCode);
        if (item == null) throw new RuntimeException("物品不存在");
        if (item.getStockQty() < qty) throw new RuntimeException("库存不足！");

        item.setStockQty(item.getStockQty() - qty);
        itemRepo.save(item);

        StockTransaction tx = new StockTransaction();
        tx.setItem(item);
        tx.setType("OUT");
        tx.setQuantity(qty);
        tx.setAmount(item.getPrice().multiply(BigDecimal.valueOf(qty)));
        tx.setTime(LocalDateTime.now());
        tx.setRemark(remark);
        stockRepo.save(tx);
    }

    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    public List<StockTransaction> getStockHistory(Long itemId) {
        return stockRepo.findByItem_IdOrderByTimeDesc(itemId);
    }
}