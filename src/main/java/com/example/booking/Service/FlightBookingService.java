package com.example.booking.Service;

import com.example.booking.Entity.FlightBooking;

public interface FlightBookingService {
    FlightBooking findById(long id);
    void save(FlightBooking flightBooking);
}
