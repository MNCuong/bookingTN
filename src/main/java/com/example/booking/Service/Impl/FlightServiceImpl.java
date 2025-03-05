package com.example.booking.Service.Impl;

import com.example.booking.Entity.Flight;
import com.example.booking.Repository.FlightBookingRepository;
import com.example.booking.Repository.FlightRepository;
import com.example.booking.Service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    @Override
    public Flight findById(Long id) {
        return flightRepository.findById(id).get();
    }

    @Override
    public void save(Flight flight) {
        flightRepository.save(flight);
    }
}
