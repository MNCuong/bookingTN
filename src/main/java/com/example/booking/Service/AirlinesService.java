package com.example.booking.Service;

import com.example.booking.Entity.Airlines;

import java.util.List;

public interface AirlinesService {
    List<Airlines> findAll();
    Airlines findById(Long id);
    Airlines findByName(String name);
}
