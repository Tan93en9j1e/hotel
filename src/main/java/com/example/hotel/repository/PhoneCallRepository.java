// src/main/java/com/example/hotel/repository/PhoneCallRepository.java
package com.example.hotel.repository;

import com.example.hotel.model.PhoneCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PhoneCallRepository extends JpaRepository<PhoneCall, Long> {
    List<PhoneCall> findByRoomNumberAndSettledFalse(String roomNumber);
    List<PhoneCall> findByRoomNumber(String roomNumber);
}