package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String bookingId;
    @ManyToOne
    private User user;
    @ManyToOne
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double totalPrice;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();


}
