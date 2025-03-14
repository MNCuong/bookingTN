package com.example.booking.DTO.Request;


import com.example.booking.Enum.TypeServiceEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PayRequest {
    BigDecimal amount_raw;
    String bankCode;
    String bookingId;
    TypeServiceEnum typeService;
    String userEmail;
}
