package com.example.booking.Repository;

import com.example.booking.Entity.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
}
