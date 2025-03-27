package com.example.booking.Mapper;

import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Entity.Flight;
import org.springframework.stereotype.Component;


import java.util.List;

public interface FlightMapper {
    FlightResponse toFlightResponse(Flight flight);
    List<FlightResponse> toFlightResponseList(List<Flight> flights);
}
