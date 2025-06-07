package com.example.booking.Repository;

import com.example.booking.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findBySeatNumberAndFlight_Id(String seatNumber, Long id);
}
