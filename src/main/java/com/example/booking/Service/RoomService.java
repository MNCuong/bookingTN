package com.example.booking.Service;

import com.example.booking.DTO.Response.RoomResponse;
import com.example.booking.DTO.Response.RoomResponse2;
import com.example.booking.Entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {
    RoomResponse addRoom(Long hotelId, double price, String type, int capacity, boolean availability, List<MultipartFile> imgs);

    RoomResponse roomDetail(Long id);

    Room getRoom(Long id);

    List<RoomResponse2> getRoomFromHotel(Long hotelId);
    List<RoomResponse2> getAllRoom();
    List<RoomResponse2> getListStandardRoom();
    List<RoomResponse2> getListSingleRoom();
    List<RoomResponse2> getListDoubleRoom();
    List<String> getImgRoom(String hotelId, String roomType, String roomId);
    RoomResponse updateStateRoom(Long roomId,String state);
    RoomResponse updateImgRoom(Long roomId,List<MultipartFile> imgs);


}
