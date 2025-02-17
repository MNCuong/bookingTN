package com.example.booking.DTO.Request;


import lombok.Data;

@Data
public class HotelRequest {
    private String name;
    private String description;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String created_at;
//    private List<String> imgs;


}
