package com.example.booking.DTO.Response;

import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.AirportInfo;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;

@Data
@Builder
public class FlightResponse {
    private Long id;
    private Airlines airline;
    private AirportInfo departureAirport;
    private AirportInfo arrivalAirport;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private BigDecimal priceEconomy;
    private BigDecimal priceBusiness;
    private Aircraft aircraft;



}
