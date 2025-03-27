package com.example.booking.Service.Impl;

import com.example.booking.Entity.AirportInfo;
import com.example.booking.Repository.AirportInfoRepository;
import com.example.booking.Service.AirPortInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AirPortInfoServiceImpl implements AirPortInfoService {
    private final AirportInfoRepository repository;
    @Override
    public AirportInfo findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public AirportInfo findByIata(String iata) {
        return repository.findByIata(iata);
    }
}
