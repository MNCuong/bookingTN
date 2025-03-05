package com.example.booking.DTO.Request;


import com.example.booking.Enum.TypeServiceEnum;
import lombok.Data;

@Data
public class PayRequest {
    long amount_raw;
    String bankCode;
    long bookingId;
    TypeServiceEnum typeService;

}
