package com.example.booking.Elasticsearch.Service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.booking.Elasticsearch.Entity.HotelDocument;
import com.example.booking.Elasticsearch.Repo.HotelDocRepository;
import com.example.booking.Elasticsearch.Service.HotelServiceElastic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
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
    private final ElasticsearchOperations elasticsearchOperations;

    public HotelServiceElasticImpl(ElasticsearchClient elasticsearchClient, HotelDocRepository hotelDocRepository, ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchClient = elasticsearchClient;
        this.hotelDocRepository = hotelDocRepository;
        this.elasticsearchOperations = elasticsearchOperations;
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


    public List<HotelDocument> searchHotelByName(String name) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(m -> m
                        .field("name")
                        .query(name)
                        .operator(Operator.Or) // Chỉ cần chứa từ khóa, không cần khớp toàn bộ
                ))
                .build();

        SearchHits<HotelDocument> searchHits = elasticsearchOperations.search(query, HotelDocument.class);
        return searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }
}
