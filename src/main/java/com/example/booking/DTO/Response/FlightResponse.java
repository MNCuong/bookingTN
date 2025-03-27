package com.example.booking.DTO.Response;

import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.AirportInfo;
import com.example.booking.Entity.FlightDetails;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class FlightResponse {
    private Airlines airline;
    private AirportInfo departureAirport;
    private AirportInfo arrivalAirport;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal priceEconomy;
    private BigDecimal priceBusiness;
    private FlightDetails flightDetails;
    private Aircraft aircraft;



}
