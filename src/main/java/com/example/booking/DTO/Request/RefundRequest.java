package com.example.booking.DTO.Request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RefundRequest {
    private String OrderId;
    private long Amount;
    private LocalDateTime TransDate;
    private String TransType;
}
