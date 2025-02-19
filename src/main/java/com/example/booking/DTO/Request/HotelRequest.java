package com.example.booking.DTO.Request;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class HotelRequest {
    private String name;
    private String description;
    private String phone;
    private String address;
    private String city;
    private String country;
    private LocalDateTime created_at;
//    private List<String> imgs;


}
