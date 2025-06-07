package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
public class BookingRequest {
    private Long flightId;
//    private Map<String, PassengerInfos> passengerInfos;
    private Long aircraftId;
    private Long departureId;
    private Long arrivalId;
    private String transactionId;
    private String flightCode;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
//
//    @Data
//    public static class PassengerInfos {
//        private String fullName;
//        private LocalDate dateOfBirth;
//        private String gender;
//        private String nationality;
//        private String personalCode;
//        private String seatNumber;
//        private String ticketType;
//        private BigDecimal price;
//    }
}

