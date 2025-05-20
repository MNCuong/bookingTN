package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.AirlineRequest;
import com.example.booking.Entity.Airlines;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AirlinesService {
    List<Airlines> getAllAirlines();
//    Airlines findById(Long id);
//    Airlines findByCode(String code);
    Page<Airlines> getAllAirlines(int page, int size, String search);
    Airlines getAirlineById(Long id);
    Airlines createAirline(AirlineRequest airlineRequest, MultipartFile img);
    Airlines updateAirline(Long id, AirlineRequest updatedAirline);
    void deleteAirline(Long id);
}
