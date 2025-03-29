package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Service.AircraftService;
import com.example.booking.Service.FlightService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/flight/aircraft")
public class AircraftController {
    private final AircraftService aircraftService;

    private final JwtUtil jwtUtil;

    @PostMapping("/add-aircraft")
    public ResponseEntity<ResponseDto<AircraftResponse>> addAircraft(@RequestBody AircraftRequest aircraftRequest) {
        return ResponseConfig.success(aircraftService.addAircraft(aircraftRequest));
    }

    @GetMapping("/list-aircraft-by-airline")
    public ResponseEntity<ResponseDto<List<AircraftResponse>>> listAircraft(HttpServletRequest request) {
        return ResponseConfig.success(aircraftService.getListAircraft(request));
    }



}
