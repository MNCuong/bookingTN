package com.example.booking.Mapper.Impl;

import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Mapper.AircraftMapper;
import org.springframework.stereotype.Component;

@Component
public class AircraftMapperImpl implements AircraftMapper {
    @Override
    public AircraftResponse toAircraftResponse(Aircraft aircraft) {
        if (aircraft == null) {
            return null;
        }
        return AircraftResponse.builder()
                .id(aircraft.getId())
                .airlines(aircraft.getAirlines())
                .iata(aircraft.getIata())
                .icao24(aircraft.getIcao24())
                .icao(aircraft.getIcao())
                .registration(aircraft.getRegistration())
                .build();
    }
}
