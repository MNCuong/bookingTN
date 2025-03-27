package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Enum.CabinClassEnums;
import lombok.Data;

@Data
public class MinPriceRequest {
    private String fromId; // Departure location Id
    private String toId;   // Arrival location Id
    private String departDate; // Departure date (YYYY-MM-DD)
    private String returnDate; // Return date (optional, YYYY-MM-DD)
    private CabinClassEnums cabinClass;
    private String currencyCode; // Curre
}
