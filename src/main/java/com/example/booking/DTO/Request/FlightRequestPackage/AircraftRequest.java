package com.example.booking.DTO.Request.FlightRequestPackage;

import com.example.booking.Entity.Airlines;
import com.example.booking.Enum.AircraftStatusEnum;
import com.example.booking.Enum.AircraftTypeEnums;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AircraftRequest {
    private String registration;

    private AircraftStatusEnum status;
    private AircraftTypeEnums typeEnums;
    private int seat;

}
