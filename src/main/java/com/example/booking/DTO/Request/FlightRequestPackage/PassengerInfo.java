package com.example.booking.DTO.Request.FlightRequestPackage;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PassengerInfo {
    private PassengerRequest passenger;
    private BigDecimal price;
}