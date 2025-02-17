package com.example.booking.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelResponse {
    private String name;
    private String description;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String created_at;
}
