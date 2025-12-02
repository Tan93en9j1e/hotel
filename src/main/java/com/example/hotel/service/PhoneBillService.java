// src/main/java/com/example/hotel/service/PhoneBillService.java
package com.example.hotel.service;

import com.example.hotel.entity.PhoneCall;
import com.example.hotel.repository.PhoneCallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhoneBillService {

    @Autowired
    private PhoneCallRepository phoneCallRepository;

    // 计费规则：市话 0.2 元/分钟，长途 1.5 元/分钟（简化）
    public BigDecimal calculateCost(String phoneNumber, int minutes) {
        if (phoneNumber.startsWith("0")) {
            return BigDecimal.valueOf(minutes).multiply(BigDecimal.valueOf(1.5));
        } else {
            return BigDecimal.valueOf(minutes).multiply(BigDecimal.valueOf(0.2));
        }
    }

    public PhoneCall recordCall(String guestName, String roomNumber, String phoneNumber, int minutes) {
        PhoneCall call = new PhoneCall();
        call.setGuestName(guestName);
        call.setRoomNumber(roomNumber);
        call.setPhoneNumber(phoneNumber);
        call.setStartTime(LocalDateTime.now());
        call.setDurationMinutes(minutes);
        call.setCost(calculateCost(phoneNumber, minutes));
        call.setSettled(false);
        return phoneCallRepository.save(call);
    }

    public List<PhoneCall> getUnsettledCalls(String roomNumber) {
        return phoneCallRepository.findByRoomNumberAndSettledFalse(roomNumber);
    }

    public List<PhoneCall> getAllCalls(String roomNumber) {
        return phoneCallRepository.findByRoomNumber(roomNumber);
    }

    public void settleCalls(String roomNumber) {
        List<PhoneCall> calls = getUnsettledCalls(roomNumber);
        for (PhoneCall call : calls) {
            call.setSettled(true);
        }
        phoneCallRepository.saveAll(calls);
    }
}