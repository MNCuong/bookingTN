package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.FlightDetailsRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.FlightDetails;

import java.util.Optional;

public interface FlightDetailsService {
    FlightDetails findById(Long id);
    FlightDetails saveRequest(FlightDetailsRequest request);
    FlightDetails save(FlightDetails flightDetails);
    Optional<FlightDetails> findByNumberOrIataOrIcaoAndAirline(String number, String iata, String icao, Airlines airline);
}
