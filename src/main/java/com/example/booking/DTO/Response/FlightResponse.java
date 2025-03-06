package com.example.booking.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightResponse {
    private String airlineName;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTime;
    private String arrivalTime;
    private String price;

}
