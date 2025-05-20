package com.example.booking.DTO.Response.FlightResponsePackage;

import com.example.booking.Entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class FlightStatusHistoryResponse {
    private Long id;
    private Flight flight;
    private String oldStatus;
    private String newStatus;
    private LocalDateTime changedAt;
}
