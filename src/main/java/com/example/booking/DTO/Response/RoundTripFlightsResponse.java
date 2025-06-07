package com.example.booking.DTO.Response;

import com.example.booking.DTO.Response.FlightResponsePackage.FlightsResponse;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class RoundTripFlightsResponse {
    private Page<FlightsResponse> departureFlights;
    private Page<FlightsResponse> returnFlights;
}
