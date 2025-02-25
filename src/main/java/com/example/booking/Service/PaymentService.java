package com.example.booking.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest;

public interface PaymentService {
    String getPay(long amount, String bankCode, HttpServletRequest request) throws UnsupportedEncodingException;
    int orderReturn(HttpServletRequest request);
}
