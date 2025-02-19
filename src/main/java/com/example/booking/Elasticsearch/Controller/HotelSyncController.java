package com.example.booking.Elasticsearch.Controller;

import com.example.booking.Elasticsearch.Service.Impl.HotelSyncServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelSyncController {

    private final HotelSyncServiceImpl hotelSyncService;

    // API để đồng bộ dữ liệu từ DB lên Elasticsearch
    @PostMapping("/sync")
    public String syncHotels() {
        hotelSyncService.syncHotelsToElasticsearch();
        return "Dữ liệu đã được đồng bộ lên Elasticsearch!";
    }
}

