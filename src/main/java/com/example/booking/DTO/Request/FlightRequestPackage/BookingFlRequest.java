package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingFlRequest {

    private List<PassengerInfo> passengerInfos;
    private BigDecimal totalAmount;
    private String transactionNo;
    private String paymentStatus;
    private Long flightId;

}
