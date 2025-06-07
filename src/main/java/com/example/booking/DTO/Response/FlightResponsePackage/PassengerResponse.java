package com.example.booking.DTO.Response.FlightResponsePackage;

import lombok.Data;

@Data
public class PassengerResponse {
    private Long id;

    private String fullName;

    private String passportNumber;

    private String nationalId;

    private String nationality;
    private String email;
}
