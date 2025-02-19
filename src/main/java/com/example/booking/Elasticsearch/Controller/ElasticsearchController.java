package com.example.booking.Elasticsearch.Controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.InfoResponse;
import com.example.booking.Elasticsearch.Entity.HotelDocument;
import com.example.booking.Elasticsearch.Service.HotelServiceElastic;
import com.example.booking.Elasticsearch.Service.Impl.HotelServiceElasticImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/es")
public class ElasticsearchController {

    private final ElasticsearchClient elasticsearchClient;
    private final HotelServiceElastic hotelServiceElastic;

    public ElasticsearchController(ElasticsearchClient elasticsearchClient, HotelServiceElastic hotelServiceElastic) {
        this.elasticsearchClient = elasticsearchClient;
        this.hotelServiceElastic = hotelServiceElastic;
    }

    @GetMapping("/info")
    public String getClusterInfo() throws Exception {
        InfoResponse info = elasticsearchClient.info();
        return "Connected to Elasticsearch Cluster: " + info.clusterName();
    }

    @GetMapping("/search")
    public List<HotelDocument> searchHotels(@RequestParam String keyword) throws IOException {
        return hotelServiceElastic.searchHotels(keyword);
    }
    @GetMapping("/searchdetail")
    public Optional<HotelDocument> searchDocumentsByField(@RequestParam String keyword) throws IOException {
        return hotelServiceElastic.searchDocumentsByField(keyword);
    }

}


