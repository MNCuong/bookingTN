package com.example.booking.Elasticsearch.Repo;

import com.example.booking.Elasticsearch.Entity.RoomDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface RoomDocRepository extends ElasticsearchRepository<RoomDocument, Long> {
}
