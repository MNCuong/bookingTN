package com.example.booking.Elasticsearch.Service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.JsonData;
import com.example.booking.Elasticsearch.Entity.RoomDocument;
import com.example.booking.Elasticsearch.Repo.RoomDocRepository;
import com.example.booking.Elasticsearch.Service.RoomServiceElastic;
import lombok.AllArgsConstructor;
//import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class RoomServiceElasticImpl implements RoomServiceElastic {
    private final RoomDocRepository roomDocRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    //    public List<RoomDocument> findAvailableRooms(LocalDate checkIn, LocalDate checkOut, int guests) {
//        // Lấy danh sách ID phòng đã đặt trong khoảng thời gian này
//        List<String> bookedRoomIds = getBookedRoomIds(checkIn, checkOut);
//
//        // Tìm kiếm các phòng trống
//        BoolQueryBuilder query = QueryBuilders.boolQuery()
//                .must(QueryBuilders.rangeQuery("capacity").gte(guests)) // Đủ sức chứa
//                .mustNot(QueryBuilders.termsQuery("id", bookedRoomIds)) // Không bị đặt trước
//                .must(QueryBuilders.termQuery("availability", true)); // Phòng đang có sẵn
//
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
//        SearchHits<RoomDocument> rooms = elasticsearchRestTemplate.search(searchQuery, RoomDocument.class);
//
//        return rooms.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
//    }
//
//    private List<String> getBookedRoomIds(LocalDate checkIn, LocalDate checkOut) {
//        BoolQueryBuilder query = QueryBuilders.boolQuery()
//                .should(QueryBuilders.rangeQuery("checkIn").lt(checkOut.toString()).gte(checkIn.toString()))
//                .should(QueryBuilders.rangeQuery("checkOut").gt(checkIn.toString()).lte(checkOut.toString()))
//                .minimumShouldMatch(1);
//
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
//        SearchHits<BookingDocument> bookings = elasticsearchRestTemplate.search(searchQuery, BookingDocument.class);
//
//        return bookings.stream().map(hit -> hit.getContent().getRoomId().toString()).collect(Collectors.toList());
//    }

    @Override
    public List<RoomDocument> getAllRooms() {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.matchAll(m -> m))
                .build();

        SearchHits<RoomDocument> searchHits = elasticsearchOperations.search(query, RoomDocument.class);
        return searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

    //  Tìm kiếm theo tên
    public List<RoomDocument> searchByState(String state) {
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.match(builder -> builder.field("state")
                        .query(state)
                        .fuzziness("AUTO")))
                .build();

        SearchHits<RoomDocument> searchHits = elasticsearchOperations.search(query, RoomDocument.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    // 🔍 3. Tìm kiếm theo khoảng giá
    public List<RoomDocument> findByPriceRange(String min, String max) {
        double minValue = (min == null || min.isEmpty()) ? 0.0 : Double.parseDouble(min);
        double maxValue = (max == null || max.isEmpty()) ? Double.MAX_VALUE : Double.parseDouble(max);
        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q.range(builder ->
                        builder.field("price").gte(JsonData.of(minValue)).lte(JsonData.of(maxValue))
                ))
                .build();

        SearchHits<RoomDocument> searchHits = elasticsearchOperations.search(query, RoomDocument.class);
        return searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

}



