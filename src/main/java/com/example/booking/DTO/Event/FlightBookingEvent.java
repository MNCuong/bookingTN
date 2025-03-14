package com.example.booking.DTO.Event;

import com.example.booking.Enum.TypeServiceEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class FlightBookingEvent {
    private String bookingId;
    private String userEmail;
    private BigDecimal totalPrice;
    private TypeServiceEnum typeService;
}
