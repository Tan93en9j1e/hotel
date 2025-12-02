package com.example.hotel.service;

import com.example.hotel.model.DiningTable;
import com.example.hotel.model.OrderItem;
import com.example.hotel.repository.DiningTableRepository;
import com.example.hotel.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiningService {

    @Autowired
    private DiningTableRepository tableRepo;

    @Autowired
    private OrderItemRepository orderRepo;

    public List<DiningTable> getAllTables() {
        return tableRepo.findAll();
    }

    public DiningTable saveTable(DiningTable table) {
        return tableRepo.save(table);
    }

    public void deleteTable(String number) {
        tableRepo.deleteById(number);
    }

    public List<OrderItem> getUnbilledItems(String tableNumber) {
        return orderRepo.findByTableNumberAndBilled(tableNumber, false);
    }

    public BigDecimal calculateTotal(String tableNumber) {
        return getUnbilledItems(tableNumber).stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addOrderItem(OrderItem item) {
        // 自动将 table 状态设为“占用”
        DiningTable table = tableRepo.findById(item.getTableNumber())
                .orElse(new DiningTable(item.getTableNumber()));
        table.setStatus("占用");
        tableRepo.save(table);
        item.setBilled(false);
        orderRepo.save(item);
    }

    public void settleBill(String tableNumber) {
        orderRepo.markAllAsBilledByTable(tableNumber);
        DiningTable table = tableRepo.findById(tableNumber).orElse(null);
        if (table != null) {
            table.setStatus("空闲");
            tableRepo.save(table);
        }
    }
}