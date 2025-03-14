package com.example.booking.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "flight_bookings")
public class FlightBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookingId;
    @ManyToOne
    private User user;
    private String flightCode;
    private String seatNumber;
    private BigDecimal totalPrice;
    private String status;
    private LocalDate flightDate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalTime departureTime;
    private LocalTime arrivalTime;
}