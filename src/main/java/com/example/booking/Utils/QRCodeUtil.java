package com.example.booking.Utils;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

@Component
public class QRCodeUtil {
    public byte[] generateQRCode(String data, int width, int height) throws Exception {
        BitMatrix matrix = new MultiFormatWriter()
                .encode(data, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
        return baos.toByteArray();
    }
}
