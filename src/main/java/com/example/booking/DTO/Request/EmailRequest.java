package com.example.booking.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
}
