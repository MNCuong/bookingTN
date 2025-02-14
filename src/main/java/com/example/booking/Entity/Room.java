package com.example.booking.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Hotel hotel;
    private String type;
    private BigDecimal price;
    private int capacity;
    private boolean availability;
}

