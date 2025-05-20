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
                .createAt(aircraft.getCreateAt())
                .updateAt(aircraft.getUpdateAt())
                .registration(aircraft.getRegistration())
                .type(aircraft.getType())
                .seat(aircraft.getSeat())
                .status(aircraft.getStatus())
                .build();
    }
}
