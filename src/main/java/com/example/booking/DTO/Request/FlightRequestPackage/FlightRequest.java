package com.example.booking.DTO.Request.FlightRequestPackage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequest {

    @NotBlank
    private String flightCode;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    private Long departureAirportId;

    @NotNull
    private Long arrivalAirportId;
    @NotNull
    private Long airlineId;
    @NotBlank
    private String status;

    private String statusReason;

    @NotBlank
    private String aircraftRegistration;

    @NotNull
    private Double priceEconomy;

    @NotNull
    private Double priceBusiness;


    private String departureGate;
    private String arrivalGate;
    private int seats;
    private int availableSeats;

    private LocalDateTime checkInDeadline;
    private LocalDateTime boardingTime;

    private List<Long> crewIds;
}
