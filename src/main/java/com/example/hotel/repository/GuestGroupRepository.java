// com.example.hotel.repository.GuestGroupRepository.java
package com.example.hotel.repository;

import com.example.hotel.model.GuestGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestGroupRepository extends JpaRepository<GuestGroup, Long> {
}