package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.User;
import com.example.booking.Enum.AircraftTypeEnums;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.AircraftMapper;
import com.example.booking.Repository.AircraftRepository;
import com.example.booking.Service.AircraftService;
import com.example.booking.Service.AirlinesService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AircraftServiceImpl implements AircraftService {
    private final AircraftRepository aircraftRepository;
    private final AirlinesService airlinesService;
    private final AircraftMapper aircraftMapper;
    private final MessageCommon messageCommon;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public Aircraft findById(Long id) {
        return aircraftRepository.findById(id).orElse(null);
    }

    @Override
    public AircraftResponse addAircraft(AircraftRequest request) {
        if (request == null || request.getIata() == null || request.getIcao() == null || request.getIcao().trim().isEmpty()
                || request.getIcao().trim().length() < 3 || request.getIcao().trim().length() > 15 || request.getAirlines_id() == null || request.getAirlines_id() == 0
                || request.getRegistration() == null || request.getRegistration().trim().isEmpty() || request.getIcao24().trim().isEmpty()
                || request.getIcao24().trim().length() < 3 || request.getIcao24().trim().length() > 15) {
            throw new BookingException(ServiceMessageConstants.CREATE_AIRCRAFT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_AIRCRAFT_FAILD));
        }
        Airlines airlines = airlinesService.findById(request.getAirlines_id());

        return aircraftMapper.toAircraftResponse(aircraftRepository.save(Aircraft.builder()
                .iata(request.getIata()).icao24(request.getIcao24())
                .icao(request.getIcao())
                .type(request.getTypeEnums())
                .status(request.getStatus())
                .airlines(airlines).registration(request.getRegistration())
                .build()));
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest aircraftRequest) {
        Aircraft aircraft = aircraftRepository.findById(id).orElse(null);
        assert aircraft != null;
        return aircraftMapper.toAircraftResponse(aircraftRepository.save(Aircraft.builder()
                .iata(aircraft.getIata()).icao24(aircraft.getIcao24())
                .icao(aircraft.getIcao())
                .status(aircraft.getStatus())
                .build()));
    }

    @Override
    public List<AircraftResponse> getListAircraft(HttpServletRequest request) {
        Airlines airlines = ServiceCommon.extractAirline(request, jwtUtil, userService, airlinesService);
        return aircraftRepository.findByAirlines((airlines));
    }


}
