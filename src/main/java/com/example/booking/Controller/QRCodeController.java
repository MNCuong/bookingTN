package com.example.booking.Controller;

import com.example.booking.Common.ServiceCommon;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/qrcode")
public class QRCodeController {

//    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<byte[]> getQRCode(@RequestParam String text) {
//        byte[] qrImage = ServiceCommon.getQRCodeImage(, 250, 250);
//        if (qrImage == null) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//        return ResponseEntity.ok(qrImage);
//    }
}
