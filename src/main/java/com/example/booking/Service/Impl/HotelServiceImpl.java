package com.example.booking.Service.Impl;

//import com.example.booking.Common.ServiceCommon;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.HotelRequest;
import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Entity.Hotel;
import com.example.booking.Entity.User;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.HotelMapper;
import com.example.booking.Repository.HotelRepository;
import com.example.booking.Service.HotelService;
import com.example.booking.Service.MinIOService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final MinIOService minIOService;
    private final MessageCommon messageCommon;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Override
    public HotelResponse getHotel(long hotelId) {
        Optional<Hotel> hotel = hotelRepository.findHotelWithRooms(hotelId);
        if (hotel.isEmpty()) {
            {
                throw new BookingException(ServiceMessageConstants.HOTEL_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.HOTEL_NOT_FOUND));
            }

        }
        return hotelMapper.toHotelResponse(hotel.get());

    }


    @Override
    public HotelResponse addHotel(HotelRequest request, List<MultipartFile> imgs, HttpServletRequest httpRequest) {
        String token = JwtUtil.getTokenFromRequest(httpRequest);
        User user = userService.findUserByEmail(jwtUtil.extractUsername(token));
        if(!user.getRoles().equals("ADMIN")){
            throw new BookingException(ServiceMessageConstants.USER_HAVE_NO_RIGHT,messageCommon.getMessage(ServiceMessageConstants.USER_HAVE_NO_RIGHT));
        }
        Hotel hotel = hotelRepository.save(Hotel.builder()
                .phone(request.getPhone())
                .city(request.getCity())
                .name(request.getName())
                .address(request.getAddress())
                .country(request.getCountry())
                .city(request.getCity())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build());
        imgs.forEach(img -> {
            try {
                minIOService.uploadFileHotel(img.getInputStream(), img.getName(), img.getContentType(), hotel.getId().toString());
            } catch (IOException | MinioException e) {
                e.printStackTrace();
            }
        });

        return hotelMapper.toHotelResponse(hotel);
    }

    @Override
    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id).get();
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }


    @Override
    public List<String> getImgHotel(String hotelId) {
        return minIOService.getHotelImages(hotelId);
    }

    @Override
    public Hotel findByUser(User user) {
        return hotelRepository.findByUser(user);
    }


}
