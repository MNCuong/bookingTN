//package com.example.booking.Elasticsearch.Controller;
//
//import com.example.booking.Elasticsearch.Scheduler.FlightCrawlService;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/v1/elastic/flights")
//public class FlightControllerElastic {
//
//    private final FlightCrawlService flightCrawlService;
//
//    @GetMapping("/vietjet")
//    public String getVietjetFlights(@RequestParam String from, @RequestParam String to, @RequestParam String date) throws Exception {
//        return flightCrawlService.crawlVietjet(from, to, date);
//    }
//
//    @GetMapping("/vnairlines")
//    public String getVietnamAirlinesFlights(@RequestParam String from, @RequestParam String to, @RequestParam String date) throws Exception {
//        return flightCrawlService.crawlVietnamAirlines(from, to, date);
//    }
//
//    @GetMapping("/bamboo")
//    public String getBambooFlights(@RequestParam String from, @RequestParam String to, @RequestParam String date) throws Exception {
//        return flightCrawlService.crawlBamboo(from, to, date);
//    }
//}
