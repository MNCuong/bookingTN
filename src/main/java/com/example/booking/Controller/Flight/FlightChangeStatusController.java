package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Response.FlightResponsePackage.FlightStatusHistoryResponse;
import com.example.booking.Entity.FlightStatusHistory;
import com.example.booking.Service.FlightStatusHistoryService;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/history")
public class FlightChangeStatusController {
    private final FlightStatusHistoryService flightStatusHistoryService;

    @GetMapping("/change-flight-status")
    public ResponseEntity<ResponseDto<List<FlightStatusHistoryResponse>>> changeFlightStatus() {
        return ResponseConfig.success(flightStatusHistoryService.getAll());
    }

}
