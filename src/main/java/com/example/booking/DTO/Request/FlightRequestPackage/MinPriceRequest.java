package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Enum.CabinClassEnums;
import com.example.booking.Enum.SortEnums;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MinPriceRequest {
    private String fromId; // Departure location Id
    private String toId;   // Arrival location Id
    private String departDate; // Departure date (YYYY-MM-DD)
    private String returnDate; // Return date (optional, YYYY-MM-DD)
    private CabinClassEnums cabinClass; // Cabin class (optional: ECONOMY, PREMIUM_ECONOMY, BUSINESS, FIRST)
    private String currencyCode; // Curre
}
