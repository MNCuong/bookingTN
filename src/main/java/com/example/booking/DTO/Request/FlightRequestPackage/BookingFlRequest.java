package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingFlRequest {
    private Long departureFlightId;
    private List<String> departureSeats;
    private Long returnFlightId;
    private List<String> returnSeats;
    private List<PassengerInfo> passengerInfos;
    private BigDecimal totalAmount;
    private String transactionNo;
    private String paymentStatus;

}
