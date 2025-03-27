package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Service.FlightService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final FlightService flightService;

    @PostMapping("create-flight")
    public ResponseEntity<ResponseDto<FlightResponse>> createFlight(@RequestBody FlightRequest request) {
        return ResponseConfig.success(flightService.createFlight(request));
    }

    @GetMapping("/search-flight")
    public ResponseEntity<ResponseDto<List<FlightResponse>>> searchFlight(@RequestParam String arrival, @RequestParam String departure) {
        return ResponseConfig.success(flightService.searchFlight(arrival, departure));
    }

    @GetMapping("/all-flight")
    public ResponseEntity<ResponseDto<List<FlightResponse>>> allFlight() {
        return ResponseConfig.success(flightService.getAllFlights());
    }

    @GetMapping("/get-flight/{id}")
    public ResponseEntity<ResponseDto<FlightResponse>> getFlightById(@PathVariable Long id) {
        return ResponseConfig.success(flightService.getFlightById(id));
    }

}
