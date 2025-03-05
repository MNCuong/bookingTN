package com.example.booking.Mapper;

import com.example.booking.DTO.Response.CarResponse;
import com.example.booking.DTO.Response.ListCarResponse;
import com.example.booking.Entity.CarRental;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {
    CarResponse toCarResponse(CarRental carRental);

}
