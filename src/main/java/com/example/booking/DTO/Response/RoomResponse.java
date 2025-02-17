package com.example.booking.DTO.Response;

import com.example.booking.Entity.Hotel;
import com.example.booking.Enum.RoomTypeEnums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomResponse {
    private Hotel hotel;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private RoomTypeEnums type;
    private int capacity;
    private boolean availability;
}
