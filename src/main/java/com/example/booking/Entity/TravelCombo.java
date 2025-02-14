package com.example.booking.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "travel_combos")
public class TravelCombo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Hotel hotel;
    @ManyToOne
    private Flight flight;
    private BigDecimal price;
}
