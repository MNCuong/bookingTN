package com.example.booking.DTO.Response.FlightResponsePackage;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import com.example.booking.Entity.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class FlightsResponse {
    private Long id;
    private String flightCode;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Long departureAirportId;
    private String departureAirport;
    private Long arrivalAirportId;
    private String arrivalAirport;
    private Long airlineId;
    private String airlineName;
    private String airlineCode;
    private String status;
    private String aircraft;
//    private List<String> crewMembers;
    private Boolean isDeleted;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime updateStatusAt;
    private Double priceEconomy;
    private Double priceBusiness;
    private String departureGate;
    private String arrivalGate;
    private LocalDateTime checkInDeadline;
    private LocalDateTime boardingTime;
    private int seats;
    private int availableSeats;
}

