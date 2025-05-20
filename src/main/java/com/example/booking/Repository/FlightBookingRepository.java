package com.example.booking.Repository;

import com.example.booking.Entity.FlightBooking;
import com.example.booking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
    int countByFlightCodeAndStatus(String flightCode, String status);

    boolean existsByFlightCode(String flightFlightCode);
    @Query("SELECT fb.seatNumber FROM FlightBooking fb " +
            "WHERE fb.flightCode = :flightCode " +
            "AND fb.flightDate = :flightDate " +
            "AND fb.departureTime = :departureTime " +
            "AND fb.arrivalTime = :arrivalTime " +
            "AND fb.status = 'CONFIRMED'")
    List<String> findConfirmedSeats(
            @Param("flightCode") String flightCode,
            @Param("flightDate") LocalDate flightDate,
            @Param("departureTime") LocalTime departureTime,
            @Param("arrivalTime") LocalTime arrivalTime
    );


    List<FlightBooking> findByUser(User user);
}
