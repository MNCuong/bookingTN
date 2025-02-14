package com.example.booking.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "car_rentals")
public class CarRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private int year;
    private BigDecimal pricePerDay;
    private String location;
}
