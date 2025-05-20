package com.example.booking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;
    private String classType; // ECONOMY, BUSINESS
    private BigDecimal price;

    private boolean checkedIn;

    @ManyToOne
    @JsonIgnore
    private Passenger passenger;

    @ManyToOne
    private Flight flight;

    @ManyToOne
    @JoinColumn(name = "payment_transaction_id")
    private PaymentTransaction paymentTransaction;    private LocalDateTime createdAt;
    private LocalDateTime checkinDate;
}

