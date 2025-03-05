package com.example.booking.Service;

import com.example.booking.DTO.Request.HotelRequest;
import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Entity.Hotel;
import com.example.booking.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelService {

    HotelResponse getHotel(long hotelId);

    HotelResponse addHotel(HotelRequest request, List<MultipartFile> imgs, HttpServletRequest httpRequest);
    Hotel getHotelById(Long id);
    Hotel save(Hotel hotel);
    List<String> getImgHotel(String hotelId);
    Hotel findByUser(User user);
}
