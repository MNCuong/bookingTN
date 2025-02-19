package com.example.booking.Repository;

import com.example.booking.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.room.id = :roomId " +
            "AND (:checkIn BETWEEN b.checkIn AND b.checkOut OR " +
            ":checkOut BETWEEN b.checkIn AND b.checkOut OR " +
            "(b.checkIn BETWEEN :checkIn AND :checkOut))")
    long countOverlappingBookings(@Param("roomId") long roomId,
                                  @Param("checkIn") LocalDate checkIn,
                                  @Param("checkOut") LocalDate checkOut);

}
