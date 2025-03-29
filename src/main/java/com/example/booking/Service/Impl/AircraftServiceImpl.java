package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.User;
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
                .airlines(airlines).registration(request.getRegistration())
                .build()));
    }

    @Override
    public List<AircraftResponse> getListAircraft(HttpServletRequest request) {
        String tokenS = JwtUtil.getTokenFromRequest(request);
        String email = jwtUtil.extractUsername(tokenS);
        User user = userService.findUserByEmail(email);
        String nameAirline = user.getFullName().substring(user.getFullName().indexOf("_") + 1);
        Airlines airlines = airlinesService.findByName(nameAirline);
        return aircraftRepository.findByAirlines((airlines));
    }
}
