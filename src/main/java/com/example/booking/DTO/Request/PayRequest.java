package com.example.booking.DTO.Request;


import com.example.booking.Enum.TypeServiceEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class PayRequest {
    BigDecimal amount_raw;
    String bankCode;
    List<Long> bookingId;
    TypeServiceEnum typeService;
    String userEmail;
}
