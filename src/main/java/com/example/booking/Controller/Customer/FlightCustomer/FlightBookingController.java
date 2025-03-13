package com.example.booking.Controller.Customer.FlightCustomer;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightBookingRequest;
import com.example.booking.DTO.Response.FlightResponsePackage.FlightBookingResponse;
import com.example.booking.Service.FlightBookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer/flight")
public class FlightBookingController {
    private final FlightBookingService flightBookingService;

    @GetMapping("/availability")
    public ResponseEntity<?> checkAvailability(@RequestParam String flightCode,@RequestParam String aircraftModel) {
        int availableSeats = flightBookingService.getAvailableSeats(flightCode,aircraftModel);
        return ResponseEntity.ok(Map.of("availableSeats", availableSeats));
    }

    @GetMapping("/search-flight")
//    public ResponseEntity<ResponseDto<String>> getFlights(@RequestParam String dep, @RequestParam String arr, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate) {
    public ResponseEntity<ResponseDto<Object>> getFlights(@RequestParam String dep, @RequestParam String arr) {
        {
            String result = flightBookingService.searchFlights(dep, arr);
            Object jsonData = flightBookingService.convertToJson(result);
            return ResponseConfig.success(jsonData);
        }
    }
    @PostMapping("/flight-booking")
    public ResponseEntity<ResponseDto<FlightBookingResponse>> bookTicket(@RequestBody FlightBookingRequest request, HttpServletRequest httpServletRequest) {
        return ResponseConfig.success(flightBookingService.bookFlight(request,httpServletRequest));
    }

}
