package com.example.booking.Repository;

import com.example.booking.Entity.Hotel;
import com.example.booking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.rooms WHERE h.id = :hotelId")
    Optional<Hotel> findHotelWithRooms(@Param("hotelId") long hotelId);

    Hotel findByUser(User user);
}
