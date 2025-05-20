package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

}
