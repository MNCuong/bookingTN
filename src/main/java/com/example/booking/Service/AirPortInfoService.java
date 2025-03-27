package com.example.booking.Service;

import com.example.booking.Entity.Aircraft;
import com.example.booking.Entity.AirportInfo;

public interface AirPortInfoService {
    AirportInfo findById(Long id);
    AirportInfo findByIata(String iata);

}
