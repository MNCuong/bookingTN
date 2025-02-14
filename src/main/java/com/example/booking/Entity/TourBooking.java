package com.example.booking.Entity;
import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tour_bookings")
public class TourBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Tour tour;
    private int numPeople;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
