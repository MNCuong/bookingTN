package com.example.booking.Controller.Customer;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.BookingRequest;
import com.example.booking.DTO.Response.BookingResponse;
import com.example.booking.Entity.BookingFl;
import com.example.booking.Entity.FlightBooking;
import com.example.booking.Service.BookingFlightService;
import com.example.booking.Service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
//@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/customer/booking")
public class BookingController {
    private final BookingFlightService bookingFlightService;

//    @PostMapping("/booking-room")
//    public ResponseEntity<ResponseDto<BookingResponse>> getBookingRoom(@RequestBody BookingRequest bookingRequest, HttpServletRequest httpServletRequest) {
//        return ResponseConfig.success(bookingService.booking(bookingRequest, httpServletRequest));
//    }
    @GetMapping("/list-ticket-booking")
    public ResponseEntity<ResponseDto<List<BookingFl>>> listTicketBooking(@RequestParam String email) {
        return ResponseConfig.success(bookingFlightService.getListBooking(email));
    }}
