package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Enum.FlightStateEnum;
import com.example.booking.Service.FlightService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/create-flight")
    public ResponseEntity<ResponseDto<FlightResponse>> createFlight(@RequestBody FlightRequest request) {
        return ResponseConfig.success(flightService.createFlight(request));
    }
    @DeleteMapping("/delete-flight/{id}")
    public ResponseEntity<ResponseDto<String>> deleteFlight(@PathVariable Long id) {
        log.info("delete flight by id: {}", id);
        return ResponseConfig.success(flightService.deleteFlight(id));
    }

    @GetMapping("/search-flight")
    public ResponseEntity<ResponseDto<List<FlightResponse>>> searchFlight(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam String arrival, @RequestParam String departure) {
        return ResponseConfig.success(flightService.searchFlight(date,arrival, departure));
    }

    @GetMapping("/all-flight")
    public ResponseEntity<ResponseDto<List<FlightResponse>>> allFlight() {
        return ResponseConfig.success(flightService.getAllFlights());
    }
    @GetMapping("/all-flight-by-airline")
    public ResponseEntity<ResponseDto<List<FlightResponse>>> allFlightByAirline(HttpServletRequest request) {
        return ResponseConfig.success(flightService.getAllFlightsByAirLine(request));
    }

    @GetMapping("/get-flight/{id}")
    public ResponseEntity<ResponseDto<FlightResponse>> getFlightById(@PathVariable Long id) {
        return ResponseConfig.success(flightService.getFlightById(id));
    }

    @GetMapping("/get-flight-status")
    public ResponseEntity<ResponseDto<List<FlightResponse>>> getFlightStatus(@RequestParam String status) {
        return ResponseConfig.success(flightService.getFlightByStatus(status));

    }
    @PostMapping("/create-airline")
    public ResponseEntity<ResponseDto<FlightResponse>> createAirline(@RequestBody FlightRequest request) {
        return ResponseConfig.success(flightService.createFlight(request));
    }

    @PutMapping("/update-status-flight/{id}")
    public ResponseEntity<ResponseDto<String>> updateFlightStatus(@PathVariable Long id,@RequestParam FlightStateEnum status) {
        return ResponseConfig.success(flightService.updateStatusFlight(id,status));
    }
    @PutMapping("/update-flight/{id}")
    public ResponseEntity<ResponseDto<FlightResponse>> updateFlight(@PathVariable Long id,@RequestBody FlightRequest request) {
        return ResponseConfig.success(flightService.updateFlight(id,request));
    }
    @GetMapping("/seats/{flightId}")
    public ResponseEntity<ResponseDto<Integer>> getSeat(@PathVariable Long flightId) {
        return ResponseConfig.success(flightService.getSeat(flightId));
    }
}
