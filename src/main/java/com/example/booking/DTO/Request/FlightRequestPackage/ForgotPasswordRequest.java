package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String email;
    private String newPassword;
}
