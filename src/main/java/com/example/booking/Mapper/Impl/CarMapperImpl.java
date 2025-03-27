package com.example.booking.Mapper.Impl;

import com.example.booking.DTO.Response.CarResponse;
import com.example.booking.Entity.CarRental;
import com.example.booking.Mapper.CarMapper;
import org.springframework.stereotype.Component;

@Component
public class CarMapperImpl implements CarMapper {
    @Override
    public CarResponse toCarResponse(CarRental carRental) {
        if(carRental == null) {
            return null;
        }
        return CarResponse.builder()
                .brand(carRental.getBrand())
                .model(carRental.getModel())
                .description(carRental.getDescription())
                .year(carRental.getYear())
                .fuelType(carRental.getFuelType())
                .status(carRental.getStatus())
                .pricePerDay(carRental.getPricePerDay())
                .seatCapacity(carRental.getSeatCapacity())
                .licensePlate(carRental.getLicensePlate())
                .hotelName(carRental.getHotel().getName())
                .build();
    }
}
