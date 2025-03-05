package com.example.booking.Entity;

import com.example.booking.Enum.CarStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String description;
    private String fuelType;//nhiên liệu
    private String imageUrl;
    private String licensePlate;//biển số
    private int seatCapacity;//số chỗ

    @Enumerated(EnumType.STRING)
    private CarStatus status;


    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;


}
