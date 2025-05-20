package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
public class BookingFl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String personalCode;
    private String seatNumber;
    private String ticketType;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "departure_id")
    private AirportInfo departure;

    @ManyToOne
    @JoinColumn(name = "arrival_id")
    private AirportInfo arrival;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;
    private LocalDateTime flightDate;
    private LocalDate dateOfBirth;
    private LocalTime departureTime ;
    private LocalTime arrivalTime ;
    private LocalDateTime createdAt;
    private String gender;
    private String nationality;
    private String transactionId;
}


