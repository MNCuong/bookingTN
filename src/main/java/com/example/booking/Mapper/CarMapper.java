package com.example.booking.Mapper;

import com.example.booking.DTO.Response.CarResponse;
import com.example.booking.Entity.CarRental;

public interface CarMapper {
    CarResponse toCarResponse(CarRental carRental);

}
