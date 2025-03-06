package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Data;

@Data
public class SearchFlightLocationRequest {
    String languageCode;
    String query;
}
