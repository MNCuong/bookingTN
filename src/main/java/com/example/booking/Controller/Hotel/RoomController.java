package com.example.booking.Controller.Hotel;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.RoomRequest;
import com.example.booking.DTO.Response.RoomResponse;
import com.example.booking.Service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/v1/admin/room")
public class RoomController {
    private final RoomService roomService;
    @PostMapping("/add-room")
    public ResponseEntity<ResponseDto<RoomResponse>> addRoom(@RequestBody RoomRequest roomRequest) {
        return ResponseConfig.success(roomService.addRoom(roomRequest));

    }
    @GetMapping("/room-detail")
    public ResponseEntity<ResponseDto<RoomResponse>> roomDetail(@RequestParam Long idRoom) {
        return ResponseConfig.success(roomService.roomDetail(idRoom));

    }
}
