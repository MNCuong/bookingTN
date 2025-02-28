package com.example.booking.Controller.Customer;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.Service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/pay")
    public ResponseEntity<ResponseDto<String>> getPay(@RequestParam long amount, @RequestParam String bankCode, HttpServletRequest request) throws Exception {
        return ResponseConfig.success(paymentService.getPay(amount, bankCode, request));
    }

//    @GetMapping("/vnpay-return")
//    public ResponseEntity<?> vnpayReturn(@RequestParam Map<String, String> params) {
//        String vnp_ResponseCode = params.get("vnp_ResponseCode");
//        String vnp_SecureHash = params.get("vnp_SecureHash");
//        if (!paymentService.verifySignature(params, vnp_SecureHash)) {
//            return ResponseEntity.badRequest().body("Invalid signature");
//        }
//        if ("00".equals(vnp_ResponseCode)) {
//            return ResponseEntity.ok("Thanh toán thành công!");
//        } else {
//            return ResponseEntity.ok("Thanh toán thất bại!");
//        }
//    }

    @GetMapping("/vnpay_returnlog")
    public ResponseEntity<ResponseDto<String>> getVNPayResponse(@RequestParam Map<String, String> params) throws Exception {
        String fullUrl = "/vnpay_returnlog?" + params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
        return ResponseConfig.success(paymentService.saveTransaction(params));
    }

}