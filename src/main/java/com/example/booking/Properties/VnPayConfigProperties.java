package com.example.booking.Properties;

public class VnPayConfigProperties {

    private final String merchantCode;
    private final String secretKey;
    private final String vnpayUrl;

    public VnPayConfigProperties(String merchantCode, String secretKey, String vnpayUrl) {
        this.merchantCode = merchantCode;
        this.secretKey = secretKey;
        this.vnpayUrl = vnpayUrl;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getVnpayUrl() {
        return vnpayUrl;
    }
}
