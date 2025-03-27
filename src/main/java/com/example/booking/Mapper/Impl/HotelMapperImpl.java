package com.example.booking.Mapper.Impl;

import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Entity.Hotel;
import com.example.booking.Mapper.HotelMapper;
import org.springframework.stereotype.Component;

@Component
public class HotelMapperImpl implements HotelMapper {
    @Override
    public HotelResponse toHotelResponse(Hotel hotel) {
        if (hotel == null) {
            return null;
        }
        return HotelResponse.builder()
                .name(hotel.getName())
                .address(hotel.getAddress())
                .phone(hotel.getPhone())
                .rooms(hotel.getRooms())
                .city(hotel.getCity())
                .description(hotel.getDescription())
                .country(hotel.getCountry())
                .created_at(hotel.getCreatedAt())
                .build();
    }
}
