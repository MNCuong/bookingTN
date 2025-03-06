package com.example.booking.Elasticsearch.Repo;

import com.example.booking.Elasticsearch.Entity.BookingDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDocRepository extends ElasticsearchRepository<BookingDocument, Long> {
}
