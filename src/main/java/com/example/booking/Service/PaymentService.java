package com.example.booking.Service;

import com.example.booking.Entity.PaymentTransaction;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface PaymentService {
    String getPay(long amount, String bankCode, HttpServletRequest request) throws Exception;
    boolean verifySignature(Map<String, String> params, String secureHash);
    String saveTransaction(Map<String, String> params);
    PaymentTransaction saveTran(Map<String, String> params);
}
