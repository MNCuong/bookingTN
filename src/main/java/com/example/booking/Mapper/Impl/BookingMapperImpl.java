package com.example.booking.Mapper.Impl;

import com.example.booking.DTO.Response.BookingResponse;
import com.example.booking.Entity.Booking;
import com.example.booking.Mapper.BookingMapper;
import org.springframework.stereotype.Component;

@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingResponse toBookingResponse(Booking booking) {
        if(booking == null){
            return null;
        }

        return BookingResponse.builder()
                .user(booking.getUser())
                .room(booking.getRoom())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .createdAt(booking.getCreatedAt())
                .totalPrice(booking.getTotalPrice())
                .build();
    }
}
