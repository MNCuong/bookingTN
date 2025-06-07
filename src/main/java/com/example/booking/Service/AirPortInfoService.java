package com.example.booking.Service;

import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.AirportInfo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AirPortInfoService {
    AirportInfo findById(Long id);

    AirportInfo findByCity(String city);

    List<AirportInfo> getList();

    Page<AirportInfo> getAll(int page, int size, String search);

    AirportInfo createAirport(AirportInfo airportInfo);
    List<AirportInfo> findAllByCity(String city);

}
