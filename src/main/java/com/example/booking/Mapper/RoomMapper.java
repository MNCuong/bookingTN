package com.example.booking.Mapper;

import com.example.booking.DTO.Response.RoomResponse;
import com.example.booking.DTO.Response.RoomResponse2;
import com.example.booking.Entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;


public interface RoomMapper {
    RoomResponse toRoom(long hotelId,Room room);
    List<RoomResponse2> toRooms(List<Room> rooms);
}
