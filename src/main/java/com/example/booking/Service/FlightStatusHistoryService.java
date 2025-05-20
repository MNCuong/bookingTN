package com.example.booking.Service;

import com.example.booking.DTO.Response.FlightResponsePackage.FlightStatusHistoryResponse;
import com.example.booking.Entity.FlightStatusHistory;

import java.util.List;

public interface FlightStatusHistoryService {
    void save(FlightStatusHistory flightStatusHistory);
    List<FlightStatusHistoryResponse> getAll();

}
