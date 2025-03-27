package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Entity.Airlines;
import lombok.Data;

@Data
public class AircraftRequest {
    private String registration;
    private String iata;
    private String icao;
    private String icao24;
    private Long airlines_id;
}
