package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.RoomRequest;
import com.example.booking.DTO.Response.RoomResponse;
import com.example.booking.Entity.Room;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.RoomMapper;
import com.example.booking.Repository.RoomRepository;
import com.example.booking.Service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    private final MessageCommon messageCommon;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomResponse addRoom(RoomRequest roomRequest) {
        if (roomRequest.getPrice() == null) {
            throw new BookingException(ServiceMessageConstants.PRICE_INVALID, messageCommon.getMessage(ServiceMessageConstants.PRICE_INVALID));
        }
        Room room = roomRepository.save(Room.builder()
                .type(roomRequest.getType())
                .price(roomRequest.getPrice())
                .hotel(roomRequest.getHotel())
                .capacity(roomRequest.getCapacity())
                .availability(true)
                .build());

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
