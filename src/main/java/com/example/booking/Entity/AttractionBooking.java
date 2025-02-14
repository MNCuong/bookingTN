package com.example.booking.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "attraction_bookings")
public class AttractionBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private AttractionTicket ticket;
    private int numTickets;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
