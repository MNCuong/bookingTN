package com.example.booking.Elasticsearch.Repo;

import com.example.booking.Elasticsearch.Entity.FlightDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightDocRepository extends ElasticsearchRepository<FlightDocument, String> {
    List<FlightDocument> findByDepartureAndArrival(String departure, String arrival);



}
