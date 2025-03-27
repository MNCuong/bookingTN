package com.example.booking.DTO.Response.FlightResponsePackage;


import lombok.Builder;
import lombok.Data;
import com.example.booking.Entity.*;

@Data
@Builder
public class FlightsResponse {
    String flightDate;

    String flightStatus;

    private AirportInfo departure;

    private AirportInfo arrival;

    private Airlines arAirlines;

    private FlightDetails flight;

    private Aircraft aircraft;
}
