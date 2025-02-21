package com.example.booking.DTO.Response;

import com.example.booking.Entity.Hotel;
import com.example.booking.Entity.Room;
import com.example.booking.Enum.RoomTypeEnums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomResponse {
    private long hotelId;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private RoomTypeEnums type;
    private int capacity;
    private boolean availability;
    private String state;
}
