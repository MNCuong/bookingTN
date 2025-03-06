package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FlightRequest {
    private String number;
    private String departure;
    private String arrival;
    private String airline;
    private String status;
}
