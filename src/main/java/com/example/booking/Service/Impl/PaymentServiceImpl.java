package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.Config.VnPayConfig;
import com.example.booking.DTO.Request.PayRequest;
import com.example.booking.Entity.PaymentTransaction;
import com.example.booking.Entity.User;
import com.example.booking.Exception.BookingException;
import com.example.booking.Repository.PaymentRepository;
import com.example.booking.Service.PaymentService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final VnPayConfig vnPayConfig;
    private final PaymentRepository paymentRepository;
    private final MessageCommon messageCommon;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public String getPay(HttpServletRequest request, PayRequest payRequest) {
        String token = JwtUtil.getTokenFromRequest(request);
        String email = jwtUtil.extractUsername(token);
        User user = userService.findUserByEmail(email);
        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = vnPayConfig.getIpAddress(request);
        long amount = payRequest.getAmount_raw() * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getVnp_TmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        if (payRequest.getBankCode() != null && !payRequest.getBankCode().isEmpty()) {
            vnp_Params.put("vnp_BankCode", payRequest.getBankCode());
        }
        String orderInfo = "Khách hàng-" + user.getId() + " : " + user.getFullName().toUpperCase(Locale.ROOT) + " thanh toán đơn hàng: " + vnp_TxnRef + ", mã đặt dịch vụ - " + payRequest.getBookingId() + ", loại dịch vụ - " + payRequest.getTypeService();
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("ETC/GMT+7"));
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.HOUR, 10);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder query = new StringBuilder();
        StringBuilder hashData = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String vnp_SecureHash = VnPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        log.info("data:{}", query.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        log.info("query:{}", vnp_SecureHash);
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + query.toString();
        log.info("Payment URL: {}", paymentUrl);
        return paymentUrl;
    }

    public boolean verifySignature(Map<String, String> params, String secureHash) {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        fieldNames.remove("vnp_SecureHash");
        try {
            StringBuilder sb = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = params.get(fieldName);
                if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                    sb.append(fieldName);
                    sb.append('=');
                    sb.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
                    if (itr.hasNext()) {
                        query.append('&');
                        sb.append('&');
                    }
                }
            }
            String vnp_SecureHash = VnPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), sb.toString());
            log.info("secureHash{}-------------------" + secureHash);
            log.info("sb:{}", sb);
            log.info("query:{}", query);
            log.info("vnp_SecureHash:{}", vnp_SecureHash);
            return vnp_SecureHash.equals(secureHash);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String saveTransaction(Map<String, String> params) {
        if (params == null || !params.containsKey("vnp_SecureHash") || !params.containsKey("vnp_ResponseCode")) {
            return "Invalid Params";
        }
        boolean verify = verifySignature(params, params.get("vnp_SecureHash"));
        if (verify) {
            String responseCode = params.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                saveTran(params);
                return "Success";
            } else {
                return "Fail";
            }
        }
        return "Invalid Signature";
    }

    @Override
    public void saveTran(Map<String, String> params) {
        String status;
        if (Objects.equals(params.get("vnp_TransactionStatus"), "00")) {
            status = "Success";
        } else {
            status = "Fail";
        }
        if (paymentRepository.existsByTransactionNo(params.get("vnp_TransactionNo"))) {
            throw new BookingException(ServiceMessageConstants.TRANS_EXIST, messageCommon.getMessage(ServiceMessageConstants.TRANS_EXIST, params.get("vnp_TransactionNo")));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.parse(params.get("vnp_PayDate"), formatter);
        LocalDate payDate = localDateTime.toLocalDate();
        String orderInfo = params.get("vnp_OrderInfo");
        String typeBooking = orderInfo.substring(orderInfo.lastIndexOf("-") + 1);
        String userId = orderInfo.split("-")[1].split(":")[0];
        User user = userService.findUserById(Long.parseLong(userId.trim())).get();
        paymentRepository.save(PaymentTransaction.builder().amount(Double.parseDouble(params.get("vnp_Amount"))).description(params.get("vnp_OrderInfo")).paymentMethod(params.get("vnp_CardType")).transactionDate(payDate).user(user).transactionNo(params.get("vnp_TransactionNo")).typeBooking(typeBooking).status(status).build());
    }
}

