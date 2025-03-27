package com.example.booking.Service.Impl;

import com.example.booking.Entity.FlightDetails;
import com.example.booking.Repository.FlightDetailsRepository;
import com.example.booking.Service.FlightDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FlightDetailsServiceImpl implements FlightDetailsService {
    private final FlightDetailsRepository flightDetailsRepository;
    @Override
    public FlightDetails findById(Long id) {
        return flightDetailsRepository.findById(id).orElse(null);
    }
}
