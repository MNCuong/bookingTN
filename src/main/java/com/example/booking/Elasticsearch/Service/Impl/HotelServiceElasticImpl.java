package com.example.booking.Elasticsearch.Service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import com.example.booking.Elasticsearch.Entity.HotelDocument;
import com.example.booking.Elasticsearch.Repo.HotelDocRepository;
import com.example.booking.Elasticsearch.Service.HotelServiceElastic;
import com.example.booking.Service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HotelServiceElasticImpl implements HotelServiceElastic {
    private final ElasticsearchClient elasticsearchClient;
private final HotelDocRepository hotelDocRepository;
    public HotelServiceElasticImpl(ElasticsearchClient elasticsearchClient, HotelDocRepository hotelDocRepository) {
        this.elasticsearchClient = elasticsearchClient;
        this.hotelDocRepository = hotelDocRepository;
    }

    @Override
    public List<HotelDocument> searchHotels(String keyword) throws IOException {
        log.info("Executing search with keyword: {}", keyword);

        Query query = Query.of(q -> q.bool(b -> b
                .should(qs -> qs.match(m -> m.field("name").query(keyword)))
                .should(qs -> qs.match(m -> m.field("address").query(keyword)))
                .should(qs -> qs.match(m -> m.field("city").query(keyword)))
                .should(qs -> qs.match(m -> m.field("description").query(keyword)))
        ));

        SearchResponse<HotelDocument> response = elasticsearchClient.search(s -> s
                        .index("hotels")
                        .query(query),
                HotelDocument.class
        );

        log.info("Response status: {}", response.shards().failed()); // Log số lượng shard lỗi
        log.info("Total hits: {}", response.hits().total()); // Log tổng số kết quả tìm thấy

        List<HotelDocument> results = response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull) // Tránh null pointer
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            log.warn("No results found for keyword: {}", keyword);
        }

        return results;
    }

    public Optional<HotelDocument> searchDocumentsByField(String field) {
        return hotelDocRepository.findById(field);
    }
}
