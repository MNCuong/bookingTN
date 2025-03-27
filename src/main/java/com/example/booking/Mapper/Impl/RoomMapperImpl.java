package com.example.booking.Mapper.Impl;

import com.example.booking.DTO.Response.RoomResponse;
import com.example.booking.DTO.Response.RoomResponse2;
import com.example.booking.Entity.Room;
import com.example.booking.Enum.RoomTypeEnums;
import com.example.booking.Mapper.RoomMapper;
import com.example.booking.Service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RoomMapperImpl implements RoomMapper {
    private final HotelService hotelService;
    @Override
    public RoomResponse toRoom(long hotelId, Room room) {
        if (room == null || hotelId <= 0) {
            return null;
        }

        RoomTypeEnums roomType = null;
        for(RoomTypeEnums type : RoomTypeEnums.values()) {
            if(type.equals(room.getType())) {
                roomType = type;
            }
        }
        RoomResponse roomResponse = RoomResponse.builder()
                .type(roomType)
                .availability(room.isAvailability())
                .hotelId(hotelId)
                .state(room.getState())
                .price(room.getPrice())
                .capacity(room.getCapacity())
                .build();
        return roomResponse;
    }

    @Override
    public List<RoomResponse2> toRooms(List<Room> rooms) {
        return List.of();
    }
}
