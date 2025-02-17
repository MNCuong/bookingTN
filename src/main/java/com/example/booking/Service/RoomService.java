package com.example.booking.Service;

import com.example.booking.DTO.Request.RoomRequest;
import com.example.booking.DTO.Response.RoomResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {
    RoomResponse addRoom(Long hotelId, double price, String type, int capacity, boolean availability, List<MultipartFile> imgs);
    RoomResponse roomDetail(Long id);

}
