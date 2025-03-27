package com.example.booking.Service;


import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponse;

import java.util.List;

public interface FlightService {


    FlightResponse createFlight(FlightRequest flightRequest);
    List<FlightResponse> getAllFlights();
    List<FlightResponse> searchFlight(String arrival,String departure);
    FlightResponse getFlightById(Long id);

}
