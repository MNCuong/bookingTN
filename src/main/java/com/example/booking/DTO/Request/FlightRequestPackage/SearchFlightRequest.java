package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Enum.CabinClassEnums;
import com.example.booking.Enum.SortEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFlightRequest {
    private String fromId; // Departure location Id
    private String toId;   // Arrival location Id
    private LocalDate departDate; // Departure date (YYYY-MM-DD)
    private LocalDate returnDate; // Return date (optional, YYYY-MM-DD)
    private Integer pageNo; // Page number (optional)
    private Integer adults; // Number of adults (optional, default 1)
    private String children; // Number of children with their ages (optional)
    private SortEnums sort; // Sorting (optional: BEST, CHEAPEST, FASTEST)
    private CabinClassEnums cabinClass; // Cabin class (optional: ECONOMY, PREMIUM_ECONOMY, BUSINESS, FIRST)
    private String currencyCode; // Currency code (optional)
}

