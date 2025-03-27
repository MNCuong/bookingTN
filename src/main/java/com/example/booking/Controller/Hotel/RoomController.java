package com.example.booking.Controller.Hotel;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.RoomRequest;
import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.DTO.Response.RoomResponse;
import com.example.booking.DTO.Response.RoomResponse2;
import com.example.booking.Enum.RoomTypeEnums;
import com.example.booking.Service.MinIOService;
import com.example.booking.Service.RoomService;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
@CrossOrigin(origins = "*")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/v1/admin/room")
public class RoomController {
    private final RoomService roomService;
    private final MinIOService minIOService;


    @PostMapping("/add-room")
    public ResponseEntity<ResponseDto<RoomResponse>> addRoom(
            @RequestParam("hotelId") Long hotelId,
            @RequestParam("price") BigDecimal price,
            @RequestParam("type") String type,
            @RequestParam("capacity") int capacity,
            @RequestParam("availability") boolean availability,
            @RequestParam("imgs") List<MultipartFile> imgs) {
        return ResponseConfig.success(roomService.addRoom(hotelId, price, type, capacity, availability, imgs));

    }

    @GetMapping("/room-detail")
    public ResponseEntity<ResponseDto<RoomResponse>> roomDetail(@RequestParam Long idRoom) {
        return ResponseConfig.success(roomService.roomDetail(idRoom));

    }

    @GetMapping("/list-room")
    public ResponseEntity<ResponseDto<List<RoomResponse2>>> listRoomFromHotel(@RequestParam Long hotelId) {
//        List<RoomResponse> arr=minIOService.listFiles();
        return ResponseConfig.success(roomService.getRoomFromHotel(hotelId));
    }

    @GetMapping("/Img-room")
    public ResponseEntity<ResponseDto<List<String>>> getImg(@RequestParam("hotelId") String hotelId,
                                                            @RequestParam("roomType") String roomType,
                                                            @RequestParam("roomId") String roomId) {
        return ResponseConfig.success(roomService.getImgRoom(hotelId, roomType, roomId));
    }

    @PutMapping("/update-state-room")
    public ResponseEntity<ResponseDto<RoomResponse>> updateStateRoom(@RequestParam("roomId") long roomId, @RequestParam("state") String state) {
        return ResponseConfig.success(roomService.updateStateRoom(roomId, state));
    }

    @PutMapping("/update-image-room")
    public ResponseEntity<ResponseDto<RoomResponse>> updateImgRoom(@RequestParam("roomId") long roomId, @RequestParam("imgs") List<MultipartFile> imgs) {
        return ResponseConfig.success(roomService.updateImgRoom(roomId, imgs));
    }
    @GetMapping("/list-all-room")
    public ResponseEntity<ResponseDto<List<RoomResponse2>>> listAllRoom() {
        return ResponseConfig.success(roomService.getAllRoom());
    }
    @GetMapping("/list-single-room")
    public ResponseEntity<ResponseDto<List<RoomResponse2>>> listSingleRoom() {
        return ResponseConfig.success(roomService.getListSingleRoom());
    }
    @GetMapping("/list-standard-room")
    public ResponseEntity<ResponseDto<List<RoomResponse2>>> listStandardRoom() {
        return ResponseConfig.success(roomService.getListStandardRoom());
    }
    @GetMapping("/list-double-room")
    public ResponseEntity<ResponseDto<List<RoomResponse2>>> listDoubleRoom() {
        return ResponseConfig.success(roomService.getListDoubleRoom());
    }
}
