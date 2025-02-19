//package com.example.booking.Controller.Customer;
//
//import com.example.booking.Service.PaymentService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/payment")
//public class VNPayReturnUrlController {
//
//        private final PaymentService paymentService;
//
//
//        @GetMapping("/vnpay-return")
//        public ResponseEntity<String> vnpayReturn(@RequestParam Map<String, String> params) {
//            // Lấy thông tin phản hồi từ VNPay
//            String vnp_ResponseCode = params.get("vnp_ResponseCode");
//            String vnp_SecureHash = params.get("vnp_SecureHash");
//
//            // Xác minh thanh toán
//            boolean isVerified = paymentService.verifyPayment(vnp_ResponseCode, vnp_SecureHash, params);
//
//            if (isVerified) {
//                return ResponseEntity.status(HttpStatus.CREATED).body("Payment success");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed");
//            }
//        }
//    }
//
//}
