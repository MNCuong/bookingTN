package com.example.booking.Repository;

import com.example.booking.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.type = 'STANDARD'")
    List<Room> findRoomByTypeStandard();
    @Query("SELECT r FROM Room r WHERE r.type = 'SINGLE'")
    List<Room> findRoomByTypeSingle();
    @Query("SELECT r FROM Room r WHERE r.type = 'DOUBLE'")
    List<Room> findRoomByTypeDouble();
}
