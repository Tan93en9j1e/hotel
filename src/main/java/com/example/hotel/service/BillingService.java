// src/main/java/com/example/hotel/service/BillingService.java
package com.example.hotel.service;

import com.example.hotel.model.Bill;
import com.example.hotel.model.BillItem;
import com.example.hotel.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepo;

    public Bill createOrUpdateBill(String guestName, String room, String type) {
        Bill bill = billRepo.findByRoomNumberAndSettledFalse(room);
        if (bill == null) {
            bill = new Bill();
            bill.setGuestName(guestName);
            bill.setRoomNumber(room);
            bill.setBillType(type);
            bill.setAdvancePayment(BigDecimal.ZERO);
            bill.setTotalAmount(BigDecimal.ZERO);
            bill.setBalance(BigDecimal.ZERO);
        }
        return billRepo.save(bill);
    }

    public void addItem(Long billId, String desc, BigDecimal amount, String category) {
        Bill bill = billRepo.findById(billId).orElseThrow();
        BillItem item = new BillItem();
        item.setBill(bill);
        item.setDescription(desc);
        item.setAmount(amount);
        item.setCategory(category);
        bill.getItems().add(item);

        // 重新计算总额
        BigDecimal total = bill.getItems().stream()
                .map(BillItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        bill.setTotalAmount(total);
        bill.setBalance(total.subtract(bill.getAdvancePayment()));
        billRepo.save(bill);
    }

    public void addAdvancePayment(Long billId, BigDecimal payment) {
        Bill bill = billRepo.findById(billId).orElseThrow();
        bill.setAdvancePayment(bill.getAdvancePayment().add(payment));
        bill.setBalance(bill.getTotalAmount().subtract(bill.getAdvancePayment()));
        billRepo.save(bill);
    }

    public void settleBill(Long billId) {
        Bill bill = billRepo.findById(billId).orElseThrow();
        bill.setSettled(true);
        billRepo.save(bill);
    }

    public Bill getActiveBillByRoom(String room) {
        return billRepo.findByRoomNumberAndSettledFalse(room);
    }

    public List<Bill> getUnsettledBills() {
        return billRepo.findBySettledFalse();
    }
}