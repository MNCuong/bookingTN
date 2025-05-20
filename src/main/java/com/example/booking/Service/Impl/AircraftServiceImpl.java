package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.FlightRequestPackage.AircraftRequest;
import com.example.booking.DTO.Response.AircraftResponse;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.AirportInfo;
import com.example.booking.Entity.User;
import com.example.booking.Enum.AircraftStatusEnum;
import com.example.booking.Enum.AircraftTypeEnums;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.AircraftMapper;
import com.example.booking.Repository.AircraftRepository;
import com.example.booking.Service.AircraftService;
import com.example.booking.Service.AirlinesService;
import com.example.booking.Service.MinIOService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AircraftServiceImpl implements AircraftService {
    private final AircraftRepository aircraftRepository;
    private final AirlinesService airlinesService;
    private final AircraftMapper aircraftMapper;
    private final MessageCommon messageCommon;
    private final UserService userService;
    private final MinIOService minIOService;

    @Override
    public Aircraft findById(Long id) {
        return aircraftRepository.findById(id).orElse(null);
    }

    @Override
    public Aircraft getByRegistration(String registration) {
        return aircraftRepository.getByRegistration(registration);
    }

    public String getImgAircraft(String registration) {
        return minIOService.getAirCraftImage(registration);
    }

    @Override
    public AircraftResponse addAircraft(AircraftRequest request, MultipartFile img) {
        if (request == null || request.getRegistration() == null || request.getRegistration().trim().isEmpty()) {
            log.info("request: {}", request);

            throw new BookingException(ServiceMessageConstants.CREATE_AIRCRAFT_FAILD, messageCommon.getMessage(ServiceMessageConstants.CREATE_AIRCRAFT_FAILD));
        }

        if (aircraftRepository.existsByRegistration(request.getRegistration())) {
            throw new BookingException(ServiceMessageConstants.REGISTRATION_EXISTED, messageCommon.getMessage(ServiceMessageConstants.REGISTRATION_EXISTED));
        }

//        Airlines airlines = airlinesService.findById(request.getAirlines_id());
        try {
            minIOService.uploadFileAriCraft(img.getInputStream(), img.getName(), img.getContentType(), request.getRegistration());
        } catch (IOException | MinioException e) {
            e.printStackTrace();
        }
        return aircraftMapper.toAircraftResponse(aircraftRepository.save(Aircraft.builder()
                .createAt(LocalDate.now())
                .type(request.getTypeEnums())
                .status(request.getStatus())
//                .airlines(1)
                .registration(request.getRegistration())
                .seat(request.getSeat())
                .build()));
    }

    @Override
    public Aircraft getAircraft(Long id) {
        return aircraftRepository.findById(id).orElse(null);
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest aircraftRequest) {
        Aircraft aircraft = aircraftRepository.findById(id).orElse(null);
        assert aircraft != null;
        aircraft.setSeat(aircraftRequest.getSeat());
        aircraft.setUpdateAt(LocalDate.now());
        aircraft.setStatus(aircraftRequest.getStatus());

        if (aircraftRequest.getTypeEnums() != null) {
            aircraft.setType(aircraftRequest.getTypeEnums());
        }
        return aircraftMapper.toAircraftResponse(aircraftRepository.save(aircraft));
    }

    @Override
    public Page<AircraftResponse> getListAircraft(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Aircraft> aircraftPage;
        if (search == null || search.isEmpty()) {
            aircraftPage = aircraftRepository.findAll(pageable);
        } else {
            aircraftPage = aircraftRepository.searchAircrafts(search, pageable);

        }
        return aircraftPage.map(aircraft -> {
            AircraftResponse response = aircraftMapper.toAircraftResponse(aircraft);
            String imageUrl = getImgAircraft(response.getRegistration());
            aircraft.setImgUrl(imageUrl);
            aircraftRepository.save(aircraft);
            response.setImageUrl(imageUrl);
            return response;
        });
    }

//    @Override
//    public List<AircraftResponse> getListAircraft(HttpServletRequest request) {
//        Airlines airlines = ServiceCommon.extractAirline(request, jwtUtil, userService, airlinesService);
//        return aircraftRepository.findByAirlines((airlines));
//    }

    @Override
    public void save(Aircraft aircraft) {
        aircraftRepository.save(aircraft);
    }

    @Override
    public List<AircraftResponse> getAvailableAircraftList() {
        List<AircraftStatusEnum> availableStatuses = Arrays.asList(AircraftStatusEnum.AVAILABLE, AircraftStatusEnum.IN_SERVICE);
        List<Aircraft> aircraftList = aircraftRepository.findByStatusIn(availableStatuses);

        return aircraftList.stream()
                .map(aircraftMapper::toAircraftResponse)
                .collect(Collectors.toList());
    }

    @Override
    public int getSeatByRegistration(String registration) {
        Aircraft aircraft = aircraftRepository.findByRegistration(registration);
        return aircraft.getSeat();
    }


}
