package com.example.booking.Service;

import com.example.booking.DTO.Request.PayRequest;
import com.example.booking.Entity.BookingFl;
import com.example.booking.Entity.PaymentTransaction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface PaymentService {
    String getPay(HttpServletRequest request, PayRequest payRequest) throws Exception;

    boolean verifySignature(Map<String, String> params, String secureHash);

    String saveTransaction(Map<String, String> params);

    void saveTran(Map<String, String> params);

    String getPayKafka(PayRequest payRequest);

    Page<PaymentTransaction> getList(int page, int size, String search);

    PaymentTransaction getPaymentDetail(Long id);

    Map<Integer, Double> getRevenueByMonth(int year);

    Map<Integer, Double> getRevenueByDayInMonth(int month, int year);

    Map<Integer, Double> getRevenueByQuarter(int quarter, int year);

    PaymentTransaction findByVnp_TransactionNo(String vnp_TransactionNo);
}
