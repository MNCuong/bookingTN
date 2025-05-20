package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Enum.AircraftTypeEnums;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AircraftService {
    Aircraft findById(Long id);
    Aircraft getByRegistration(String Registration);

    AircraftResponse addAircraft(AircraftRequest request, MultipartFile img);

    Aircraft getAircraft(Long id);

    AircraftResponse updateAircraft(Long id, AircraftRequest aircraftRequest);

    Page<AircraftResponse> getListAircraft(int page, int size,String search);

    void save(Aircraft aircraft);
    List<AircraftResponse> getAvailableAircraftList();
    int getSeatByRegistration(String registration);
}
