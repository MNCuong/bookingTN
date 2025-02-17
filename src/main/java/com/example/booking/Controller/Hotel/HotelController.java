package com.example.booking.Controller.Hotel;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.HotelRequest;
import com.example.booking.DTO.Response.AuthResponse;
import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Entity.Hotel;
import com.example.booking.Service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/hotel")
public class HotelController {
    private final HotelService hotelService;

    @PostMapping("/add-hotel")
    public ResponseEntity<ResponseDto<HotelResponse>> addHotel(@RequestBody HotelRequest request) {
        return ResponseConfig.success(hotelService.addHotel(request));
    }
}
