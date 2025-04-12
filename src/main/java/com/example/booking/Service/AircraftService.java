package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AircraftService {
    Aircraft findById(Long id);
    AircraftResponse addAircraft(AircraftRequest request);
    AircraftResponse updateAircraft(Long id, AircraftRequest aircraftRequest);
    List<AircraftResponse> getListAircraft(HttpServletRequest request);
}
