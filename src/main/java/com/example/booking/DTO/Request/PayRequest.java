package com.example.booking.DTO.Request;


import com.example.booking.Enum.TypeServiceEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PayRequest {
    long amount_raw;
    String bankCode;
    long bookingId;
    TypeServiceEnum typeService;
    String userEmail;
}
