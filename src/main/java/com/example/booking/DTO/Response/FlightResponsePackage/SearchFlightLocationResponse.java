package com.example.booking.DTO.Response.FlightResponsePackage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFlightLocationResponse {
    String type;
    String name;
    String code;
    String city;
    String cityName;
    String regionName;
    String country;
    String countryName;
    String countryNameShort;
    String photoUri;
}
