package com.example.booking.DTO.Request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CarRentalBookingsRequest {
    private long idCar;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
}
