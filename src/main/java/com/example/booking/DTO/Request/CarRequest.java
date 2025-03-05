package com.example.booking.DTO.Request;

import com.example.booking.Enum.CarStatus;
import com.example.booking.Enum.CarType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class CarRequest {
    private String brand;
    private String model;
    private int year;
    private BigDecimal pricePerDay;
    private String description;
    private String fuelType;//nhiên liệu
    private String licensePlate;//biển số
    private int seatCapacity;//số chỗ
    @Enumerated(EnumType.STRING)
    private CarType type;
    @Enumerated(EnumType.STRING)
    private CarStatus status;


}
