package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.FlightBookingRequest;
import com.example.booking.DTO.Response.FlightResponsePackage.FlightBookingResponse;
import com.example.booking.Entity.FlightBooking;
import jakarta.servlet.http.HttpServletRequest;

public interface FlightBookingService {
    FlightBooking findById(long id);

    void save(FlightBooking flightBooking);

    String searchFlights(String depIata, String arrIata);

    Object convertToJson(String jsonString);

    FlightBooking findByBookingId(String id);

    int getAvailableSeats(String flightCode, String aircraftModel);

    FlightBookingResponse bookFlight(FlightBookingRequest flightBookingRequest, HttpServletRequest httpServletRequest);
}
