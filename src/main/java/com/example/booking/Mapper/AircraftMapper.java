package com.example.booking.Mapper;

import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

public interface AircraftMapper {
    AircraftResponse toAircraftResponse(Aircraft aircraft);
}
