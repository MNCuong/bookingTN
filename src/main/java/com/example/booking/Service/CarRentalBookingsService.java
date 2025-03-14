package com.example.booking.Service;

import com.example.booking.DTO.Request.CarRentalBookingsRequest;
import com.example.booking.Entity.CarRentalBooking;
import jakarta.servlet.http.HttpServletRequest;

public interface CarRentalBookingsService {
    CarRentalBooking bookingCar(CarRentalBookingsRequest carRentalBookingsRequest, HttpServletRequest request);
    CarRentalBooking findByBookingId(String id);
    void save(CarRentalBooking carRentalBooking);
}
