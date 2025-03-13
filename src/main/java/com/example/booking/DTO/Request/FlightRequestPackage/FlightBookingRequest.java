package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Entity.UserProfileFlight;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class FlightBookingRequest {
    private List<UserProfileFlight> userProfileFlight;
    private String flightCode;
    private String aircraftModel;
    private List<String> seatNumber;
    private int seatTotal;
    private BigDecimal totalPrice;
    private LocalDate flightDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
}
