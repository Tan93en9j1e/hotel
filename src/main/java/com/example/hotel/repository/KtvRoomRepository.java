package com.example.hotel.repository;

import com.example.hotel.model.KtvRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KtvRoomRepository extends JpaRepository<KtvRoom, String> {}