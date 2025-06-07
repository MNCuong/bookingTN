package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.FlightRequest;
import com.example.booking.DTO.Response.FlightResponsePackage.FlightsResponse;
import com.example.booking.DTO.Response.RoundTripFlightsResponse;
import com.example.booking.Entity.Flight;
import com.example.booking.Service.AirPortInfoService;
import com.example.booking.Service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final AirPortInfoService airPortInfoService;

//    @PostMapping("/create-flight")
//    public ResponseEntity<ResponseDto<Flight>> createFlights(@RequestBody FlightRequest request) {
//        log.info("Flight Code: {}" , request.getFlightCode());
//        log.info("Departure Time:{} " , request.getDepartureTime());
//        log.info("Arrival Time: {}" , request.getArrivalTime());
//        log.info("Departure Airport {}: " , request.getDepartureAirportId());
//        log.info("Arrival Airport ID: {}" , request.getArrivalAirportId());
//        log.info("Status: {}" , request.getStatus());
//        log.info("Aircraft ID: {}" , request.getAircraftId());
//        return ResponseConfig.success(flightService.createFlight(request));
//    }
    @PostMapping("/create")
    public ResponseEntity<ResponseDto<FlightsResponse>> createFlight(@RequestBody FlightRequest request) {
        log.info("Flight Code: {}" , request.getFlightCode());
        log.info("Departure Time:{} " , request.getDepartureTime());
        log.info("Arrival Time: {}" , request.getArrivalTime());
        log.info("Departure Airport {}: " , request.getDepartureAirportId());
        log.info("Arrival Airport ID: {}" , request.getArrivalAirportId());
        log.info("Status: {}" , request.getStatus());
        return ResponseConfig.success(flightService.createFlight(request));
    }

    @DeleteMapping("/delete-flight/{id}")
    public ResponseEntity<ResponseDto<String>> deleteFlight(@PathVariable Long id) {
        log.info("delete flight by id: {}", id);
        return ResponseConfig.success(flightService.deleteFlight(id));
    }

    @GetMapping("/search-flight")
    public ResponseEntity<ResponseDto<RoundTripFlightsResponse>> searchFlight(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam String arrival,
            @RequestParam String departure,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
            @RequestParam(defaultValue = "oneWay") String tripType) {

        if ("roundTrip".equalsIgnoreCase(tripType) && returnDate != null) {
            Page<FlightsResponse> departureFlights = flightService.searchFlightsForDirection(page, size, startDate, departure, arrival);
            Page<FlightsResponse> returnFlights = flightService.searchFlightsForDirection(page, size, returnDate, arrival, departure);

            RoundTripFlightsResponse response = new RoundTripFlightsResponse();
            response.setDepartureFlights(departureFlights);
            response.setReturnFlights(returnFlights);

            return ResponseConfig.success(response);
        }

        // One-way
        Page<FlightsResponse> departureFlights = flightService.searchFlightsForDirection(page, size, startDate, departure, arrival);
        RoundTripFlightsResponse response = new RoundTripFlightsResponse();
        response.setDepartureFlights(departureFlights);

        return ResponseConfig.success(response);
    }


    @GetMapping("/all-flight")
    public ResponseEntity<ResponseDto<Page<FlightsResponse>>> allFlight(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<FlightsResponse> flightPage = flightService.getAllFlights(page, size);
        return ResponseConfig.success(flightPage);
    }
    @GetMapping("/{flightId}/history")
    public ResponseEntity<ResponseDto<Page<FlightsResponse>>> getFlightHistory(@PathVariable Long flightId) {
        Page<FlightsResponse> flightHistory = flightService.getFlightHistory(flightId);
        return ResponseConfig.success(flightHistory);
    }

//    @GetMapping("/all-flight-by-airline")
//    public ResponseEntity<ResponseDto<List<Flight>>> allFlightByAirline(HttpServletRequest request) {
//        return ResponseConfig.success(flightService.getAllFlightsByAirLine(request));
//    }

//    @GetMapping("/get-flight/{id}")
//    public ResponseEntity<ResponseDto<Flight>> getFlightById(@PathVariable Long id) throws Exception {
//        return ResponseConfig.success(flightService.getFlightById(id));
//    }

    @GetMapping("flight/{id}")
    public ResponseEntity<ResponseDto<FlightsResponse>> getFlightById(@PathVariable Long id) throws Exception {
        return ResponseConfig.success(flightService.getFlightById(id));
    }

    @GetMapping("/get-flight-status")
    public ResponseEntity<ResponseDto<List<Flight>>> getFlightStatus(@RequestParam String status) {
        return ResponseConfig.success(flightService.getFlightByStatus(status));

    }

//    @PostMapping("/create-flight")
//    public ResponseEntity<ResponseDto<Flight>> createFlight(@RequestBody FlightRequest request) {
//        return ResponseConfig.success(flightService.createFlight(request));
//    }

//    @PutMapping("/update-status-flight/{id}")
//    public ResponseEntity<ResponseDto<String>> updateFlightStatus(
//            @PathVariable Long id,
//            @RequestParam FlightStateEnum status
//    ) {
//        return ResponseConfig.success(flightService.updateStatusFlight(id, status));
//    }

    @PutMapping("/update-flight/{id}")
    public ResponseEntity<ResponseDto<FlightsResponse>> updateFlight(@PathVariable Long id, @RequestBody FlightRequest request) {
        return ResponseConfig.success(flightService.updateFlight(id, request));
    }

    @GetMapping("/seats/{flightId}")
    public ResponseEntity<ResponseDto<Integer>> getSeat(@PathVariable Long flightId) {
        return ResponseConfig.success(flightService.getSeat(flightId));
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<Page<FlightsResponse>>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseConfig.success(flightService.getAllFlights(page, size));
    }

}
