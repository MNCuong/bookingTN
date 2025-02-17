package com.example.booking.Service.Impl;

//import com.example.booking.Common.ServiceCommon;
import com.example.booking.DTO.Request.HotelRequest;
import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Entity.Hotel;
import com.example.booking.Mapper.HotelMapper;
import com.example.booking.Repository.HotelRepository;
import com.example.booking.Service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
//    private final ServiceCommon serviceCommon;

    @Override
    public List<Hotel> getHotels() {
        return List.of();
    }

    @Override
    public HotelResponse getHotel(HotelRequest request) {
        return null;
    }

    @Override
    public HotelResponse addHotel(HotelRequest request) {
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


}
