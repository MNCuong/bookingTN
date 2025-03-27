package com.example.booking.DTO.Response;

import com.example.booking.Entity.Airlines;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AircraftResponse {
    private Long id;
    private String registration;
    private String iata;
    private String icao;
    private String icao24;
    private Airlines airlines;
}
