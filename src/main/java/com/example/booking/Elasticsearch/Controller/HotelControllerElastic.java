//package com.example.booking.Elasticsearch.Controller;
//
//import com.example.booking.Config.ResponseConfig;
//import com.example.booking.Config.ResponseDto;
//import com.example.booking.Elasticsearch.Entity.HotelDocument;
//import com.example.booking.Elasticsearch.Entity.RoomDocument;
//import com.example.booking.Elasticsearch.Service.HotelServiceElastic;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/v1/elastic/room")
//public class HotelControllerElastic {
//    private final HotelServiceElastic hotelServiceElastic;
//
//    @GetMapping("/get-hotel")
//    public ResponseEntity<ResponseDto<List<HotelDocument>>> searchHotels(@RequestParam String name) throws IOException {
//        return ResponseConfig.success(hotelServiceElastic.searchHotels(name));
//    }
//
//}
