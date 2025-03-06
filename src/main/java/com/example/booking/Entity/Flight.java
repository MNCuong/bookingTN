package com.example.booking.Entity;
import com.example.booking.Enum.FlightStateEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flight_code", nullable = false, unique = true)
    private String flightCode;

    @Column(name = "airline", nullable = false)
    private String airline;

    @Column(name = "departure_airport", nullable = false)
    private String departureAirport;

    @Column(name = "arrival_airport", nullable = false)
    private String arrivalAirport;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "seat_capacity", nullable = false)
    private int seatCapacity;

    @Column(name = "available_seats")
    private int availableSeats;

    @Column(name = "status", nullable = false)
    private String status; // Scheduled, Cancelled, Delayed, Completed
}


