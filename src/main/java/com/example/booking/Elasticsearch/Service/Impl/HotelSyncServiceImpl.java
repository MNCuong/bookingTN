package com.example.booking.Elasticsearch.Service.Impl;

import com.example.booking.Elasticsearch.Entity.HotelDocument;
import com.example.booking.Elasticsearch.Repo.HotelDocRepository;
import com.example.booking.Entity.Hotel;
import com.example.booking.Repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class HotelSyncServiceImpl {
    private final HotelRepository hotelJpaRepository;
    private final HotelDocRepository hotelElasticRepository;

    public void syncHotelsToElasticsearch() {
        // 1. Lấy danh sách khách sạn từ database
        List<Hotel> hotelsFromDB = hotelJpaRepository.findAll();

        // 2. Chuyển đổi dữ liệu từ DB sang Elasticsearch document
        List<HotelDocument> hotelsToIndex = hotelsFromDB.stream()
                .map(h -> new HotelDocument(
                        h.getId(),
                        h.getName(),
                        h.getAddress(),
                        h.getCity(),
                        h.getCountry(),
                        h.getDescription(),
                        h.getPhone(),
                        h.getRating(),
                        h.getCreatedAt().toLocalDate()
                ))
                .collect(Collectors.toList());

        // 3. Lưu toàn bộ dữ liệu vào Elasticsearch
        hotelElasticRepository.saveAll(hotelsToIndex);
    }
}
