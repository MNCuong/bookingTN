package com.example.booking.Service;

import com.example.booking.DTO.Request.BookingRequest;
import com.example.booking.DTO.Response.BookingResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface BookingService {
    BookingResponse booking(BookingRequest bookingRequest, HttpServletRequest httpServletRequest);
}
