package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Entity.Airlines;
import com.example.booking.Enum.AircraftStatusEnum;
import com.example.booking.Enum.AircraftTypeEnums;
import lombok.Data;

@Data
public class AircraftRequest {
    private String registration;
    private String iata;
    private String icao;
    private String icao24;
    private Long airlines_id;
    private AircraftStatusEnum status;
    private AircraftTypeEnums typeEnums;
}
