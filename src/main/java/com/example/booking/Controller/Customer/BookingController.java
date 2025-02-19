package com.example.booking.Controller.Customer;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.BookingRequest;
import com.example.booking.DTO.Response.BookingResponse;
import com.example.booking.Service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer/booking")
public class BookingController {
    private final BookingService bookingService;
    @GetMapping("/booking-room")
    public ResponseEntity<ResponseDto<BookingResponse>> getBookingRoom(@RequestBody BookingRequest bookingRequest, HttpServletRequest httpServletRequest) {

return ResponseConfig.success(bookingService.booking(bookingRequest,httpServletRequest));
    }
}
