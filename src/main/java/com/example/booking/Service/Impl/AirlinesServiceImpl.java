package com.example.booking.Service.Impl;

import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.FlightRequestPackage.AirlineRequest;
import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.Airlines;
import com.example.booking.Exception.BookingException;
import com.example.booking.Repository.AirlinesRepository;
import com.example.booking.Service.AirlinesService;
import com.example.booking.Service.MinIOService;
import io.minio.errors.MinioException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AirlinesServiceImpl implements AirlinesService {

    private final AirlinesRepository airlineRepository;
    private final MinIOService minIOService;

    @Override
    public List<Airlines> getAllAirlines() {
        return airlineRepository.findAll();
    }

    public Page<Airlines> getAllAirlines(int page, int size, String search) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Airlines> airlinePage;
        if (search == null || search.isEmpty()) {
            airlinePage = airlineRepository.findAll(pageable);
        } else {
            airlinePage = airlineRepository.searchAirlinesByCode(search, pageable);

        }
        return airlinePage.map(airline -> {
            String logoUrl = getImgAirline(airline.getCode());
            airline.setLogoUrl(logoUrl);
            return airline;
        });
    }

    public Airlines getAirlineById(Long id) {
        Airlines airline = airlineRepository.findById(id).get();
        if (airline == null) {
            throw new RuntimeException("Không tìm thấy hãng bay");
        }
        airline.setLogoUrl(getImgAirline(airline.getCode()));

        return airlineRepository.save(airline);
    }

    public Airlines createAirline(AirlineRequest request, MultipartFile img) {

        if (request == null
                || request.getCode() == null) {

            throw new BookingException(ServiceMessageConstants.CREATE_AIRCRAFT_FAILD, "Mã hãng bay rỗng");
        }
        if (airlineRepository.existsByCode(request.getCode())) {
            throw new BookingException(ServiceMessageConstants.CREATE_AIRCRAFT_FAILD, "Mã hãng bay đã tồn tại");
        }
        try {
            minIOService.uploadFileAirline(img.getInputStream(), img.getName(), img.getContentType(), request.getCode());
        } catch (IOException | MinioException e) {
            e.printStackTrace();
        }
        Airlines airline = new Airlines();
        airline.setName(request.getName());
        airline.setCode(request.getCode());
        airline.setCreatedAt(LocalDateTime.now());
        return airlineRepository.save(airline);
    }

    public Airlines updateAirline(Long id, AirlineRequest updatedAirline) {


        return airlineRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedAirline.getName());
                    existing.setCode(updatedAirline.getCode());
                    existing.setUpdatedAt(LocalDateTime.now());
                    return airlineRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Airline not found"));
    }

    public void deleteAirline(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new RuntimeException("Airline not found");
        }
        airlineRepository.deleteById(id);
    }

    public String getImgAirline(String code) {
        return minIOService.getAirlineImage(code);
    }
}
