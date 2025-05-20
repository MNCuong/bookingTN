package com.example.booking.Common;


import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.User;
import com.example.booking.Service.AirlinesService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.UUID;


import java.awt.image.BufferedImage;
@AllArgsConstructor
@Component
public class ServiceCommon {

    public String generateBookingId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }
//    public static Airlines extractAirline(HttpServletRequest request, JwtUtil jwtUtil, UserService userService, AirlinesService airlinesService) {
//        String tokenS = JwtUtil.getTokenFromRequest(request);
//        String email = jwtUtil.extractUsername(tokenS);
//        User user = userService.findUserByEmail(email);
//        String nameAirline = user.getFullName().substring(user.getFullName().indexOf("_") + 1);
//        return airlinesService.findByCode(nameAirline);
//    }
    public static byte[] getQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }


}
