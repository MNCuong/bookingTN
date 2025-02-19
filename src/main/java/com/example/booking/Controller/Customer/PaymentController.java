package com.example.booking.Controller.Customer;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.Service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/pay")
    public ResponseEntity<ResponseDto<String>> getPay(@RequestParam long amount, @RequestParam String bankCode) throws UnsupportedEncodingException {
        return ResponseConfig.success(paymentService.getPay(amount, bankCode));
    }
    @GetMapping("/returnUrl")
    public ResponseEntity<ResponseDto<String>> GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =paymentService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return ResponseConfig.success(paymentStatus == 1 ? "ordersuccess" : "orderfail");
    }
}