package com.example.booking.Elasticsearch.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "rooms")
public class RoomDocument {
    @Id
    private String id;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword)
    private String type;

    @Field(type = FieldType.Integer)
    private int capacity;

    @Field(type = FieldType.Boolean)
    private boolean availability;

    @Field(type = FieldType.Long)
    private Long hotelId;

    @Field(type = FieldType.Keyword)
    private String state;
}
