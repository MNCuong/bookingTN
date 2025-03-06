//package com.example.booking.Elasticsearch.Scheduler;
//
//import org.springframework.stereotype.Service;
//import org.jsoup.Jsoup;
//
//@Service
//public class FlightCrawlService {
//
//    public String crawlVietjet(String from, String to, String date) throws Exception {
//        String url = "https://vietjetair.com/vi/booking/flight-search?from=" + from + "&to=" + to + "&date=" + date;
//        return Jsoup.connect(url).ignoreContentType(true)
//                .userAgent("Mozilla/5.0 (Windows NT 11.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
//                .execute().body();
//    }
//
//    public String crawlVietnamAirlines(String from, String to, String date) throws Exception {
//        String url = "https://www.vietnamairlines.com/api/v1/flight/search?origin=" + from + "&destination=" + to + "&departureDate=" + date;
//        return Jsoup.connect(url).ignoreContentType(true)
//                .userAgent("Mozilla/5.0 (Windows NT 11.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
//                .execute().body();
//    }
//
//    public String crawlBamboo(String from, String to, String date) throws Exception {
//        String url = "https://www.bambooairways.com/api/search-flights?from=" + from + "&to=" + to + "&date=" + date;
//        return Jsoup.connect(url).ignoreContentType(true)
//                .userAgent("Mozilla/5.0 (Windows NT 11.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
//                .execute().body();
//    }
//}
//
//
