package com.example.booking.Service;

import com.example.booking.Entity.Flight;

public interface FlightService {
    Flight findById(Long id);
    void save(Flight flight);
}
