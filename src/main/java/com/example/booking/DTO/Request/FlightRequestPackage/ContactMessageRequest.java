package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageRequest {
    private String name;
    private String email;
    private String subject;
    private String message;
    private Long id;
}
