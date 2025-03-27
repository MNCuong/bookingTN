package com.example.booking.Mapper;

import com.example.booking.DTO.Response.BookingResponse;
import com.example.booking.Entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

public interface BookingMapper {
    BookingResponse toBookingResponse(Booking booking);
}
