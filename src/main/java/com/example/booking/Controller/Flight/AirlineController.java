package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Request.FlightRequestPackage.AirlineRequest;
import com.example.booking.Entity.Airlines;
import com.example.booking.Exception.BookingException;
import com.example.booking.Service.AirlinesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/airlines")
@CrossOrigin
public class AirlineController {

    @Autowired
    private AirlinesService airlineService;

    @GetMapping
    public  ResponseEntity<ResponseDto<Page<Airlines>>> getAll( @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,@RequestParam String search) {
        return ResponseConfig.success(airlineService.getAllAirlines(page, size,search));
    }
    @GetMapping("/list")
    public  ResponseEntity<ResponseDto<List<Airlines>>> getAll() {
        return ResponseConfig.success(airlineService.getAllAirlines());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Airlines>> getById(@PathVariable Long id) {
        return ResponseConfig.success(airlineService.getAirlineById(id));
    }

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<ResponseDto<Airlines>> create( @RequestPart("data") String data,
                                                          @RequestPart(value = "file", required = false) MultipartFile img) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AirlineRequest airlineRequest = objectMapper.readValue(data, AirlineRequest.class);

        if (img == null || img.getSize() == 0|| img.isEmpty()) {
            throw new BookingException("Loi roiii");

        }
        return  ResponseConfig.success(airlineService.createAirline(airlineRequest,img));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Airlines>> update(@PathVariable Long id, @RequestBody AirlineRequest airline) {
        return  ResponseConfig.success(airlineService.updateAirline(id, airline));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        airlineService.deleteAirline(id);
        return ResponseEntity.noContent().build();
    }
}

