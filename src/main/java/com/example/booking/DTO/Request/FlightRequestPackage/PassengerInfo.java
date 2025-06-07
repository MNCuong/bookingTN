package com.example.booking.DTO.Request.FlightRequestPackage;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PassengerInfo {
//    private PassengerRequest passenger;
//    private BigDecimal price;
//    private String tripType;
    private String name;
    private String passportNumber;
    private String nationalId;
    private String nationality;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String number;
    private String type;



}