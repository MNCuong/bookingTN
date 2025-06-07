package com.example.booking.Service;

import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.BookingFl;
import com.example.booking.Entity.FlightBooking;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingFlightService {
    List<BookingFl> getListBooking(String email);
    String save(BookingFl bookingFl,HttpServletRequest httpServletRequest);
    Page<BookingFl> getAllBooking( int page, int size);
    BookingFl getBooking(long id);

}
