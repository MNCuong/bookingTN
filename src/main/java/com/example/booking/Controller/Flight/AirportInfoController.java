package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.Entity.AirportInfo;
import com.example.booking.Service.AirPortInfoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/airport-info")
public class AirportInfoController {
    private final AirPortInfoService airPortInfoService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<List<AirportInfo>>> getList() {
        return ResponseConfig.success(airPortInfoService.getList());
    }

    @GetMapping("/list-airport")
    public ResponseEntity<ResponseDto<Page<AirportInfo>>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam String search) {
        return ResponseConfig.success(airPortInfoService.getAll(page, size, search));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<AirportInfo>> createAirport(@RequestBody AirportInfo airportInfo) {
        return ResponseConfig.success(airPortInfoService.createAirport(airportInfo));
    }
}
