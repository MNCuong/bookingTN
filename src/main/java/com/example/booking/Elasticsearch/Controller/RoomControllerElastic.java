//package com.example.booking.Elasticsearch.Controller;
//
//import com.example.booking.Config.ResponseConfig;
//import com.example.booking.Config.ResponseDto;
//import com.example.booking.Elasticsearch.Entity.RoomDocument;
//import com.example.booking.Elasticsearch.Service.RoomServiceElastic;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/v1/elastic/room")
//public class RoomControllerElastic {
//    private final RoomServiceElastic roomSearchService;
//
//    @GetMapping("/all-room")
//    public ResponseEntity<ResponseDto<List<RoomDocument>>> getAllRoomElastic(){
//        return ResponseConfig.success(roomSearchService.getAllRooms());
//    }
//    @GetMapping("/search-room")
//    public ResponseEntity<ResponseDto<List<RoomDocument>>> searchRoom(@RequestParam String state){
//        return ResponseConfig.success(roomSearchService.searchByState(state));
//    }
//    @GetMapping("/price-range-room")
//    public ResponseEntity<ResponseDto<List<RoomDocument>>> searchRoom(@RequestParam String min, @RequestParam String max){
//        return ResponseConfig.success(roomSearchService.findByPriceRange(min, max));
//    }
//}
