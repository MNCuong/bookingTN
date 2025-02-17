package com.example.booking.Service;

import com.example.booking.DTO.Request.HotelRequest;
import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Entity.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> getHotels();
    HotelResponse getHotel(HotelRequest request);
    HotelResponse addHotel(HotelRequest request);
    HotelResponse updateHotel(HotelRequest request);
    HotelResponse deleteHotel(HotelRequest request);
}
