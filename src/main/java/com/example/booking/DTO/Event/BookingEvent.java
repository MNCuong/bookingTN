package com.example.booking.DTO.Event;

import com.example.booking.Enum.TypeServiceEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingEvent {
    private long bookingId;
    private String userEmail;
    private BigDecimal totalPrice;
    private TypeServiceEnum typeService;
}

