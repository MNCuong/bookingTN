package com.example.booking.DTO.Response;

import com.example.booking.Entity.Room;
import com.example.booking.Entity.User;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookingResponse {
    private User user;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double totalPrice;
    private LocalDateTime createdAt;
}
