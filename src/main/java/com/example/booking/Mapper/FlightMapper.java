package com.example.booking.Mapper;

import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FlightMapper {
    FlightResponse toFlightResponse(Flight flight);
}
