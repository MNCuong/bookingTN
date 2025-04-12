package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Entity.CodeSharedFlight;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class FlightDetailsRequest {
    private String number;
    private String iata;
    private String icao;
    private Long codesharedId;
}
