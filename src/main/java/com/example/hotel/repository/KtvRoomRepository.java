package com.example.hotel.repository;

import com.example.hotel.entity.KtvRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KtvRoomRepository extends JpaRepository<KtvRoom, String> {}