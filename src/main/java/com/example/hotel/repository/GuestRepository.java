// File: src/main/java/com/example/hotel/repository/GuestRepository.java

package com.example.hotel.repository;

import com.example.hotel.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByRoomNumber(String roomNumber);

    // 自定义查询：按姓名或房间号模糊搜索
    @Query("SELECT g FROM Guest g WHERE " +
            "LOWER(g.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(g.roomNumber) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Guest> findByNameContainingOrRoomNumberContainingIgnoreCase(@Param("query") String query);
}