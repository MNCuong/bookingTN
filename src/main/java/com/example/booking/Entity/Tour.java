package com.example.booking.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String destination;
    private int duration;
    private BigDecimal price;
}
