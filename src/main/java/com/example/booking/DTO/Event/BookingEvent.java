package com.example.booking.DTO.Event;

import com.example.booking.Enum.TypeServiceEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingEvent {
    private long bookingId;
    private String userEmail;
    private Double totalPrice;
    private TypeServiceEnum typeService;
}

