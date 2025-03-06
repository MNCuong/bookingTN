package com.example.booking.Controller.Customer.FlightCustomer;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.MinPriceRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.SearchFlightLocationRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.SearchFlightRequest;
import com.example.booking.Service.FlightService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer/flight")
public class FlightController {
    private final FlightService flightService;

    //    public ResponseEntity<ResponseDto<JsonNode>> searchFlight(@RequestBody SearchFlightRequest request) {
//        return ResponseConfig.success(flightService.searchFlights(request));
//    }
//
//    @GetMapping("/search-flight-location")
//    public ResponseEntity<ResponseDto<JsonNode>> searchFlightLocation(@RequestBody SearchFlightLocationRequest request) {
//        return ResponseConfig.success(flightService.searchFlightLocation(request));
//    }
//
//    @GetMapping("/get-min-price")
//    public ResponseEntity<ResponseDto<JsonNode>> getMinPrice(@RequestBody MinPriceRequest request) {
//        return ResponseConfig.success(flightService.getMinPrice(request));
//    }
    @GetMapping("/search-flight")
//    public ResponseEntity<ResponseDto<String>> getFlights(@RequestParam String dep, @RequestParam String arr, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate flightDate) {
    public ResponseEntity<ResponseDto<Object>> getFlights(@RequestParam String dep, @RequestParam String arr) {
        {
            String result = flightService.searchFlights(dep, arr);
            Object jsonData = flightService.convertToJson(result);

            return ResponseConfig.success(jsonData);
        }
    }
}
