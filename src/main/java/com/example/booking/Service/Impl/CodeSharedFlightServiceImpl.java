package com.example.booking.Service.Impl;

import com.example.booking.Entity.CodeSharedFlight;
import com.example.booking.Repository.CodeSharedFlightRepository;
import com.example.booking.Service.CodeSharedFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CodeSharedFlightServiceImpl implements CodeSharedFlightService {

    private final CodeSharedFlightRepository codeSharedFlightRepository;
    @Override
    public CodeSharedFlight findById(Long id) {
        return codeSharedFlightRepository.findById(id).orElse(null);
    }
}
