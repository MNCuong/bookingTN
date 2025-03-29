package com.example.booking.Service.Impl;

import com.example.booking.Entity.Airlines;
import com.example.booking.Repository.AirlinesRepository;
import com.example.booking.Service.AirlinesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AirlinesServiceImpl implements AirlinesService {
    private final AirlinesRepository repository;
    @Override
    public List<Airlines> findAll() {
        return repository.findAll();
    }

    @Override
    public Airlines findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Airlines findByName(String name) {
        return repository.findByName(name);
    }
}
