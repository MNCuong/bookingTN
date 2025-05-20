package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Data;

@Data
public class PassengerRequest {
    private String fullName;
    private String passportNumber;
    private String nationalId;
    private String nationality;
    private String email;
    private String birthDate;
    private String gender;
    private String number;
    private String type;

}
