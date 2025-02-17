package com.example.booking.Service;

import com.example.booking.DTO.Request.RoomRequest;
import com.example.booking.DTO.Response.RoomResponse;

public interface RoomService {
    RoomResponse addRoom(RoomRequest roomRequest);
    RoomResponse roomDetail(Long id);

}
