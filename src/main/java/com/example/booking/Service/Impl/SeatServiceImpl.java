package com.example.booking.Service.Impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.booking.Entity.Seat;
import com.example.booking.Service.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SeatServiceImpl implements SeatService {
//    private final SocketIOServer socketIOServer;

    @Override
    public void sendSeatStatusUpdate(Long flightId, List<Seat> updatedSeats) {
//        socketIOServer.getRoomOperations("flight-" + flightId).sendEvent("seatUpdate", updatedSeats);
    }
}
