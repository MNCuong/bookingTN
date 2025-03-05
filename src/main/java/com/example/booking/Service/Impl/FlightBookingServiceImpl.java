package com.example.booking.Service.Impl;

import com.example.booking.Entity.FlightBooking;
import com.example.booking.Repository.FlightBookingRepository;
import com.example.booking.Service.FlightBookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FlightBookingServiceImpl implements FlightBookingService {
    private final FlightBookingRepository flightBookingRepository;
    @Override
    public FlightBooking findById(long id) {
        return flightBookingRepository.findById(id).orElse(null);
    }

    @Override
    public void save(FlightBooking flightBooking) {
        flightBookingRepository.save(flightBooking);
    }

}
