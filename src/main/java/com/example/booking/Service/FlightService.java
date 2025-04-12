package com.example.booking.Service;


import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Enum.FlightStateEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {


    FlightResponse createFlight(FlightRequest flightRequest);

    FlightResponse updateFlight(Long id, FlightRequest flightRequest);

    String deleteFlight(Long id);

    List<FlightResponse> getAllFlights();

    List<FlightResponse> getAllFlightsByAirLine(HttpServletRequest request);

    List<FlightResponse> searchFlight(LocalDate date,String arrival, String departure);

    FlightResponse getFlightById(Long id);

    List<FlightResponse> getFlightByStatus(String status);

    String updateStatusFlight(Long id, FlightStateEnum status);
    int getSeat(Long id);

}
