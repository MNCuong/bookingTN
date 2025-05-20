package com.example.booking.DTO.Response.FlightResponsePackage;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FlightBookingResponse {
    String flightBookingId;
    String transactionId;
    String status;
    String message;
}
