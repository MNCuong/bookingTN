package com.example.booking.DTO.Response.FlightResponsePackage;

import com.example.booking.Entity.Flight;
import com.example.booking.Entity.Passenger;
import com.example.booking.Entity.PaymentTransaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TicketResponse {
    private Long id;

    private String seatNumber;
    private String classType; // ECONOMY, BUSINESS
    private BigDecimal price;
    private boolean checkedIn;
    private Long passengerId;
    private String passengerFullName;
    private Long flightId;
    private String flightCode;
    private String transactionNo;
    private LocalDateTime createdAt;
    private LocalDateTime checkinDate;
}
