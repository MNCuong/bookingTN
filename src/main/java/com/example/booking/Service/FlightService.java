package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.MinPriceRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.SearchFlightLocationRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.SearchFlightRequest;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;


public interface FlightService {
    //    Flight findById(Long id);
//    void save(Flight flight);
//    FlightResponse addFlight(FlightRequest flightRequest);
//    List<FlightResponse> getFlights(String lat, String lon);

    String searchFlights(String depIata, String arrIata);

    Object convertToJson(String jsonString);

//    int getAvailableSeats(String flightCode);

}
