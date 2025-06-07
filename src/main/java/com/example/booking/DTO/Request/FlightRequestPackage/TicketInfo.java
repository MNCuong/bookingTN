package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@AllArgsConstructor
@Data
public class TicketInfo {
    private String number;
    private String type;
    private Double price;
}
