package com.example.hotel.repository;

import com.example.hotel.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// GuestRepository.java
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByGroupId(Long groupId);
    void deleteByGroupId(Long groupId); // ← 新增这一行
}