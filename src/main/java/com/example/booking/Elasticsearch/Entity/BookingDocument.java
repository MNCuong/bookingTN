//package com.example.booking.Elasticsearch.Entity;
//
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Document(indexName = "bookings")
//public class BookingDocument {
//    @Id
//    private String id;
//
//    @Field(type = FieldType.Long)
//    private Long userId;
//
//    @Field(type = FieldType.Long)
//    private Long roomId;
//
//    @Field(type = FieldType.Date)
//    private LocalDate checkIn;
//
//    @Field(type = FieldType.Date)
//    private LocalDate checkOut;
//
//    @Field(type = FieldType.Double)
//    private BigDecimal totalPrice;
//
//    @Field(type = FieldType.Keyword)
//    private String status;
//
//    @Field(type = FieldType.Date)
//    private LocalDateTime createdAt;
//}
//
