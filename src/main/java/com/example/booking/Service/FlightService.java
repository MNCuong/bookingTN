package com.example.booking.Service;


import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponsePackage.FlightsResponse;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.Flight;
import com.example.booking.Enum.FlightStateEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {

    Page<FlightsResponse> getFlightHistory(Long flightId);

    FlightsResponse createFlight(FlightRequest flightRequest);

    FlightsResponse updateFlight(Long id, FlightRequest flightRequest);

    String deleteFlight(Long id);

//    List<Flight> getAllFlights();

//    List<Flight> getAllFlightsByAirLine(HttpServletRequest request);

    Page<FlightsResponse> searchFlightsForDirection(int page, int size,LocalDate date, String arrival, String departure);

    FlightsResponse getFlightById(Long id);

    Flight getFlightByIdFlight(Long id) throws Exception;

    List<Flight> getFlightByStatus(String status);

    //    String updateStatusFlight(Long id, FlightStateEnum status);
    int getSeat(Long id);

    //    List<Airlines>  getAllAriline();
    Page<FlightsResponse> getAllFlights(int page, int size);

}
