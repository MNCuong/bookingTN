package com.example.booking.Controller.Customer;

import java.util.Map;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.PayRequest;
import com.example.booking.Entity.PaymentTransaction;
import com.example.booking.Service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<ResponseDto<String>> getPay(HttpServletRequest request, @RequestBody PayRequest payRequest) throws Exception {
        return ResponseConfig.success(paymentService.getPay(request, payRequest));
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

    @GetMapping("/vnpay_return")
    public ResponseEntity<ResponseDto<String>> getVNPayResponse(@RequestParam Map<String, String> params) {
        String fullUrl = "/vnpay_return?" + params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
        log.info("getVNPayResponse: {}", fullUrl);
        return ResponseConfig.success(paymentService.saveTransaction(params));
    }

    @GetMapping("/list-transaction")
    public ResponseEntity<ResponseDto<Page<PaymentTransaction>>> getList(@RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size,
                                                                         @RequestParam String search) {
        return ResponseConfig.success(paymentService.getList(page, size, search));
    }

    @GetMapping("/payment-detail/{id}")
    public ResponseEntity<ResponseDto<PaymentTransaction>> getPaymentDetail(@PathVariable Long id) {
        return ResponseConfig.success(paymentService.getPaymentDetail(id));
    }

    @GetMapping("/revenue/monthly")
    public ResponseEntity<Map<Integer, Double>> getMonthlyRevenue(@RequestParam int year) {
        return ResponseEntity.ok(paymentService.getRevenueByMonth(year));
    }

    @GetMapping("/revenue/daily")
    public ResponseEntity<Map<Integer, Double>> getDailyRevenue(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(paymentService.getRevenueByDayInMonth(month, year));
    }

    @GetMapping("/revenue/quarter")
    public ResponseEntity<Map<Integer, Double>> getQuarterRevenue(@RequestParam int quarter, @RequestParam int year) {
        return ResponseEntity.ok(paymentService.getRevenueByQuarter(quarter, year));
    }
}