package com.example.booking.DTO.Response;

import com.example.booking.Enum.CarStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Builder
@Data
public class CarResponse {
    private String brand;
    private String model;
    private int year;
    private BigDecimal pricePerDay;
    private String description;
    private String fuelType;//nhiên liệu
    private String licensePlate;//biển số
    private int seatCapacity;//số chỗ

    @Enumerated(EnumType.STRING)
    private CarStatus status;
    private String hotelName;
}
