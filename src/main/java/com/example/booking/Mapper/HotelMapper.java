package com.example.booking.Mapper;

import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

public interface HotelMapper {
    HotelResponse toHotelResponse(Hotel hotel);
}
