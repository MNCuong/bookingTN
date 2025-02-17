package com.example.booking.Service.Impl;

//import com.example.booking.Common.ServiceCommon;
import com.example.booking.DTO.Request.HotelRequest;
import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Entity.Hotel;
import com.example.booking.Mapper.HotelMapper;
import com.example.booking.Repository.HotelRepository;
import com.example.booking.Service.HotelService;
import com.example.booking.Service.MinIOService;
import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final MinIOService minIOService;

    @Override
    public List<Hotel> getHotels() {
        return List.of();
    }

    @Override
    public HotelResponse getHotel(HotelRequest request) {
        return null;
    }

    @Override
    public HotelResponse addHotel(HotelRequest request,List<MultipartFile> imgs) {
        Hotel hotel = hotelRepository.save(Hotel.builder()
                .phone(request.getPhone())
                .city(request.getCity())
                .name(request.getName())
                .address(request.getAddress())
                .country(request.getCountry())
                .city(request.getCity())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build());
        imgs.forEach(img -> {
            try {
                minIOService.uploadFileHotel(img.getInputStream(),img.getName(),img.getContentType(),hotel.getId().toString());
            } catch (IOException | MinioException e) {
                e.printStackTrace();
            }
        });

        return hotelMapper.toHotelResponse(hotel);
    }


    @Override
    public HotelResponse updateHotel(HotelRequest request) {
        return null;
    }

    @Override
    public HotelResponse deleteHotel(HotelRequest request) {
        return null;
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).orElse(null);
    }


}
