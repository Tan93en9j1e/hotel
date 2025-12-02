package com.example.hotel.repository;

import com.example.hotel.model.SongOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongOrderRepository extends JpaRepository<SongOrder, Long> {
    List<SongOrder> findByKtvRoomNumberAndBilled(String roomNumber, Boolean billed);

    @Modifying
    @Query("UPDATE SongOrder s SET s.billed = true WHERE s.ktvRoomNumber = :roomNumber AND s.billed = false")
    void markAllAsBilledByRoom(String roomNumber);
}