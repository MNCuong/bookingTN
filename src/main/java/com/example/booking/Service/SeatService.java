package com.example.booking.Service;

import com.example.booking.Entity.Seat;

import java.util.List;

public interface SeatService {
    void sendSeatStatusUpdate(Long flightId, List<Seat> updatedSeats);
}
