package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.RoomRequest;
import com.example.booking.DTO.Response.RoomResponse;
import com.example.booking.Entity.Hotel;
import com.example.booking.Entity.Room;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.RoomMapper;
import com.example.booking.Repository.RoomRepository;
import com.example.booking.Service.HotelService;
import com.example.booking.Service.MinIOService;
import com.example.booking.Service.RoomService;
import io.minio.errors.MinioException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    private final MessageCommon messageCommon;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final MinIOService minIOService;
    private final HotelService hotelService;


    @Transactional
    @Override
    public RoomResponse addRoom(Long hotelId, double price, String type, int capacity, boolean availability, List<MultipartFile> imgs) {
        if (price == 0) {
            throw new BookingException(ServiceMessageConstants.PRICE_INVALID, messageCommon.getMessage(ServiceMessageConstants.PRICE_INVALID));
        }
        Hotel hotel=hotelService.getHotelById(hotelId);
        Room room = roomRepository.save(Room.builder()
                .type(type)
                .price(price)
                .hotel(hotel)
                .capacity(capacity)
                .availability(true)
                .build());
        imgs.forEach(img -> {
            try {
                minIOService.uploadFile(img.getInputStream(),img.getName(),img.getContentType(),hotelId.toString(),type,room.getId());
            } catch (IOException | MinioException e) {
                e.printStackTrace();
            }
        });
        return roomMapper.toRoom(room);
    }

    @Override
    public RoomResponse roomDetail(Long id) {
        if (id == null || id == 0) {
            throw new BookingException(ServiceMessageConstants.ID_ROOM_INVALID,
                    messageCommon.getMessage(ServiceMessageConstants.ID_ROOM_INVALID));
        }
        Room roomDetail = roomRepository.findById(id).orElse(null);
        if (roomDetail == null) {
            throw new BookingException(ServiceMessageConstants.ROOM_NOT_FOUND,
                    messageCommon.getMessage(ServiceMessageConstants.ROOM_NOT_FOUND));
        }
        return roomMapper.toRoom(roomDetail);
    }
}
