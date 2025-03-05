package com.example.booking.DTO.Response;

import com.example.booking.Entity.CarRental;
import com.example.booking.Entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarRentalBookingsResponse {
    private User user;
    private CarRental carRental;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;
}
