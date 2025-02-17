package com.example.booking.Service.Impl;

import com.example.booking.Repository.BookingRepository;
import com.example.booking.Service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

}
