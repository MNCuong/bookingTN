package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Data;

@Data
public class RegisterFlightRequest {
    private String email;
    private String password;
    private String full_name;
    private String phone_number;
    private String iata;
    private String icao;
    private String name;

}


