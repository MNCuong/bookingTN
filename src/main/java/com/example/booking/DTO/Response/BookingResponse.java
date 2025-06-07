package com.example.booking.DTO.Response;

import com.example.booking.Entity.User;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Builder
@Data
public class BookingResponse {
    private User user;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
