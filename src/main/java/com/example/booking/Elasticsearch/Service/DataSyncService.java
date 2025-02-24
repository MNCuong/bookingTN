package com.example.booking.Elasticsearch.Service;

import com.example.booking.Elasticsearch.Entity.BookingDocument;
import com.example.booking.Elasticsearch.Entity.HotelDocument;
import com.example.booking.Elasticsearch.Entity.RoomDocument;
import com.example.booking.Elasticsearch.Repo.BookingDocRepository;
import com.example.booking.Elasticsearch.Repo.HotelDocRepository;
import com.example.booking.Elasticsearch.Repo.RoomDocRepository;
import com.example.booking.Entity.Booking;
import com.example.booking.Entity.Hotel;
import com.example.booking.Entity.Room;
import com.example.booking.Repository.BookingRepository;
import com.example.booking.Repository.HotelRepository;
import com.example.booking.Repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DataSyncService {

    private final RoomDocRepository roomDocRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingDocRepository bookingDocRepository;
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




    public void syncRoomsToElasticsearch() {
        List<Room> roomsFromDB = roomRepository.findAll();

        List<RoomDocument> roomDocuments = roomsFromDB.stream().map(room ->
                RoomDocument.builder()
                        .id(String.valueOf(room.getId()))
                        .price(room.getPrice())
                        .type(room.getType())
                        .capacity(room.getCapacity())
                        .availability(room.isAvailability())
                        .hotelId(room.getHotel().getId())
                        .state(room.getState())
                        .build()
        ).collect(Collectors.toList());

        roomDocRepository.saveAll(roomDocuments);
    }

    public void syncBookingsToElasticsearch() {
        List<Booking> bookingFromDB = bookingRepository.findAll();

        List<BookingDocument> bookingDocuments = bookingFromDB.stream().map(booking ->
                BookingDocument.builder()
                        .id(String.valueOf(booking.getId()))
                        .userId(booking.getUser().getId())
                        .roomId(booking.getRoom().getId())
                        .checkIn(booking.getCheckIn())
                        .checkOut(booking.getCheckOut())
                        .totalPrice(booking.getTotalPrice())
                        .status(booking.getStatus())
                        .createdAt(booking.getCreatedAt())
                        .build()
        ).collect(Collectors.toList());

        bookingDocRepository.saveAll(bookingDocuments);
    }
}
