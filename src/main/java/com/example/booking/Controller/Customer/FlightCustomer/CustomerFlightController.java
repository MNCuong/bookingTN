package com.example.booking.Controller.Customer.FlightCustomer;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.DTO.Response.UserProfileFlightResponse;
import com.example.booking.Service.UserProfileFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer/")
public class CustomerFlightController {
    private final UserProfileFlightService userProfileFlightService;


//    @GetMapping("/list-customer")
//    public ResponseEntity<ResponseDto<List<UserProfileFlightResponse>>> getAllUserProfileFlight() {
//        return ResponseConfig.success(userProfileFlightService.getAllUserProfileFlight());
//
//    }
}
