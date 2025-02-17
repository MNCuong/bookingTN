package com.example.booking.DTO.Request;

import com.example.booking.Entity.Hotel;
import com.example.booking.Enum.RoomTypeEnums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomRequest {
    private Long hotelId;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private RoomTypeEnums type;
    private int capacity;//số luọng người
    private boolean availability;//trạng thái phòng

}
