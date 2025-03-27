package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.RoomRequest;
import com.example.booking.DTO.Response.RoomResponse;
import com.example.booking.DTO.Response.RoomResponse2;
import com.example.booking.Entity.Hotel;
import com.example.booking.Entity.Room;
import com.example.booking.Enum.RoomStateEnums;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.RoomMapper;
import com.example.booking.Repository.BookingRepository;
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
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public RoomResponse addRoom(Long hotelId, BigDecimal price, String type, int capacity, boolean availability, List<MultipartFile> imgs) {
        if (price== null) {
            throw new BookingException(ServiceMessageConstants.PRICE_INVALID, messageCommon.getMessage(ServiceMessageConstants.PRICE_INVALID));
        }
        Hotel hotel = hotelService.getHotelById(hotelId);
        Room room = roomRepository.save(Room.builder()
                .type(type)
                .price(price)
                .hotel(hotel)
                .capacity(capacity)
                .availability(true)
                .state(RoomStateEnums.AVAILABLE.toString())
                .build());
        hotel.getRooms().add(room);
        hotelService.save(hotel);
        imgs.forEach(img -> {
            try {
                minIOService.uploadFile(img.getInputStream(), img.getName(), img.getContentType(), hotelId.toString(), type, room.getId());
            } catch (IOException | MinioException e) {
                e.printStackTrace();
            }
        });
        return roomMapper.toRoom(room.getHotel().getId(),room);
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
        return roomMapper.toRoom(roomDetail.getHotel().getId(),roomDetail);
    }

    @Override
    public Room getRoom(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public List<RoomResponse2> getRoomFromHotel(Long hotelId) {
        Hotel exHotel = hotelService.getHotelById(hotelId);
        if (exHotel == null) {
            throw new BookingException(ServiceMessageConstants.HOTEL_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.HOTEL_NOT_FOUND));
        }
        List<Room> rooms = exHotel.getRooms();
        return roomMapper.toRooms(rooms);
    }


    @Override
    public List<RoomResponse2> getAllRoom() {
        return roomMapper.toRooms(roomRepository.findAll());
    }

    @Override
    public List<RoomResponse2> getListStandardRoom() {
        return roomMapper.toRooms(roomRepository.findRoomByTypeStandard());
    }

    @Override
    public List<RoomResponse2> getListSingleRoom() {
        return roomMapper.toRooms(roomRepository.findRoomByTypeSingle());
    }
    @Override
    public List<RoomResponse2> getListDoubleRoom() {
        return roomMapper.toRooms(roomRepository.findRoomByTypeDouble());
    }

    @Override
    public List<String> getImgRoom(String hotelId, String roomType, String roomId) {
        return minIOService.getRoomImages(hotelId, roomType, roomId);
    }

    public RoomResponse updateStateRoom(Long roomId, String state) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BookingException(ServiceMessageConstants.ROOM_NOT_FOUND,
                        messageCommon.getMessage(ServiceMessageConstants.ROOM_NOT_FOUND)));
        room.setState(state);
        return roomMapper.toRoom(room.getHotel().getId(),roomRepository.save(room));
    }

    @Override
    public RoomResponse updateImgRoom(Long roomId, List<MultipartFile> imgs) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BookingException(ServiceMessageConstants.ROOM_NOT_FOUND,
                        messageCommon.getMessage(ServiceMessageConstants.ROOM_NOT_FOUND)));
        imgs.forEach(img -> {
            try {
                minIOService.uploadFile(img.getInputStream(), img.getName(), img.getContentType(), room.getHotel().getId().toString(), room.getType(), room.getId());
            } catch (IOException | MinioException e) {
                e.printStackTrace();
            }
        });
        return roomMapper.toRoom(room.getHotel().getId(),room);
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).get();
    }

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

}
