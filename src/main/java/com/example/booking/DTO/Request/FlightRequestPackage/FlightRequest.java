package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Enum.FlightStateEnum;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
public class FlightRequest {
    private LocalDateTime flight_date;
    private LocalDateTime flight_date_land;
    private Long departure_id;
    private Long arrival_id;
    private Long airline_id;
    private Long aircraft_id;
    private FlightDetailsRequest flight_details_request;
    private FlightStateEnum flight_status;
    private BigDecimal priceEconomy;
    private BigDecimal priceBusiness;
    private Boolean isDeleted;

}
