package com.example.booking.Service;

import com.example.booking.DTO.Request.PayRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    String getPay(HttpServletRequest request, PayRequest payRequest) throws Exception;
    boolean verifySignature(Map<String, String> params, String secureHash);
    String saveTransaction(Map<String, String> params);
    void saveTran(Map<String, String> params);
    String getPayKafka(PayRequest payRequest);
}
