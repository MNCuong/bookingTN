package com.example.booking.Service.Impl;

import com.example.booking.Entity.AirportInfo;
import com.example.booking.Entity.Flight;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.AircraftMapper;
import com.example.booking.Mapper.FlightMapper;
import com.example.booking.Repository.AirportInfoRepository;
import com.example.booking.Service.AirPortInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AirPortInfoServiceImpl implements AirPortInfoService {
    private final AirportInfoRepository repository;

    @Override
    public AirportInfo findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public AirportInfo findByCity(String city) {
        return repository.findByCity(city);
    }

    @Override
    public List<AirportInfo> getList() {
        return repository.findAll();
    }

    @Override
    public Page<AirportInfo> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        if (search == null || search.isEmpty()) {
            return repository.findAll(pageable);
        } else {
            return repository.findByAnyField(search, pageable);
        }
    }

    @Override
    public AirportInfo createAirport(AirportInfo airportInfo) {
        if (repository.existsByAirport(airportInfo.getAirport())) {
            throw new BookingException("Error", "Sân bay đã tồn tại");
        }
        if (repository.existsByCode(airportInfo.getCode())) {
            throw new BookingException("Error", "Mã sân bay đã tồn tại");
        }
        return repository.save(airportInfo);
    }

    @Override
    public List<AirportInfo> findAllByCity(String city) {
        return repository.findAllByCity(city);
    }


}
