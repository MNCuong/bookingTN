package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Exception.BookingException;
import com.example.booking.Service.AircraftService;
import com.example.booking.Service.FlightService;
import com.example.booking.Utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/flight/aircraft")
public class AircraftController {
    private final AircraftService aircraftService;

    private final JwtUtil jwtUtil;

    @PostMapping(value = "/add-aircraft", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<AircraftResponse>> addAircraft(
            @RequestPart("data") String data,
            @RequestPart(value = "file", required = false) MultipartFile img) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        AircraftRequest aircraftRequest = objectMapper.readValue(data, AircraftRequest.class);

        if (img == null || img.getSize() == 0|| img.isEmpty()) {
            throw new BookingException("Loi roiii");

        }

        return ResponseConfig.success(aircraftService.addAircraft(aircraftRequest, img));
    }





    @PutMapping("/update-aircraft/{id}")
    public ResponseEntity<ResponseDto<AircraftResponse>> updateAircraft(@PathVariable Long id, @RequestBody AircraftRequest aircraftRequest) {
        return ResponseConfig.success(aircraftService.updateAircraft(id, aircraftRequest));
    }

    @GetMapping("/get-aircraft/{id}")
    public ResponseEntity<ResponseDto<Aircraft>> getAircraft(@PathVariable Long id) {
        return ResponseConfig.success(aircraftService.getAircraft(id));
    }
    @GetMapping("/available-aircraft")
    public ResponseEntity<ResponseDto<List<AircraftResponse>>> listAvailableAircraft() {
        List<AircraftResponse> availableAircraftList = aircraftService.getAvailableAircraftList();
        return ResponseConfig.success(availableAircraftList);
    }


    @GetMapping("/list-aircraft-by-airline")
    public ResponseEntity<ResponseDto<Page<AircraftResponse>>> listAircraft( @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size,@RequestParam String search) {
        return ResponseConfig.success(aircraftService.getListAircraft( page, size,search));
    }


}
