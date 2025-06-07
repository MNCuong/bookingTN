package com.example.booking.Service.Impl;

import com.example.booking.DTO.Response.FlightResponsePackage.FlightStatusHistoryResponse;
import com.example.booking.Entity.FlightStatusHistory;
import com.example.booking.Repository.FlightStatusHistoryRepository;
import com.example.booking.Service.FlightStatusHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class FlightStatusHistoryServiceImpl implements FlightStatusHistoryService {
    private FlightStatusHistoryRepository repository;
    @Override
    public void save(FlightStatusHistory flightStatusHistory) {
        repository.save(flightStatusHistory);
    }

    @Override
    public List<FlightStatusHistoryResponse> getAll() {
        List<FlightStatusHistory> flightStatusHistoryList = repository.findAll();
        List<FlightStatusHistoryResponse> flightStatusHistoryResponseList = new ArrayList<>();
        for(FlightStatusHistory flightStatusHistory : flightStatusHistoryList) {

            flightStatusHistory.setNewStatus(flightStatusHistory.getNewStatus());


        }
return null;
//        return repository.findAll();
    }
}
