//package com.example.booking.Elasticsearch.Controller;
//
//import com.example.booking.Elasticsearch.Service.DataSyncService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/sync/")
//public class SyncDataController {
//    private final DataSyncService dataSyncService;
//
//    @GetMapping("/data-elastic")
//    public String dataElastic() {
//        dataSyncService.syncHotelsToElasticsearch();
//        dataSyncService.syncRoomsToElasticsearch();
//        dataSyncService.syncBookingsToElasticsearch();
//        return "Dữ liệu đã được đồng bộ lên Elasticsearch!";
//    }
//}
