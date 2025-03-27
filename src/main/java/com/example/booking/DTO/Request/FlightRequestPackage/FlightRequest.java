package com.example.booking.DTO.Request.FlightRequestPackage;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class FlightRequest {
    private LocalDateTime fight_date;
    private Long departure_id;
    private Long arrival_id;
    private Long airline_id;
    private Long flight_details_id;
    private Long aircraft_id;
    private String flight_status;
}
