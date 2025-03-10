package com.example.booking.Controller.Customer;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.CarRentalBookingsRequest;
import com.example.booking.Entity.CarRentalBooking;
import com.example.booking.Service.CarRentalBookingsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer/car-rental-booking")
public class CarRentalBookingsController {
    private final CarRentalBookingsService carRentalBookingsService;

    @PostMapping("/booking-car")
    public ResponseEntity<ResponseDto<CarRentalBooking>> bookingCar(@ModelAttribute CarRentalBookingsRequest carRentalBookingsRequest, HttpServletRequest request) {
        return ResponseConfig.success(carRentalBookingsService.bookingCar(carRentalBookingsRequest,request));
    }
}
