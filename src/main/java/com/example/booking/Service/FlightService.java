package com.example.booking.Service;


import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Enum.FlightStateEnum;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface FlightService {


    FlightResponse createFlight(FlightRequest flightRequest);
    List<FlightResponse> getAllFlights();
    List<FlightResponse> searchFlight(String arrival,String departure);
    FlightResponse getFlightById(Long id);
    List<FlightResponse> getFlightByStatus(String status);
    String updateStatusFlight(Long id, FlightStateEnum status);

}
