package com.example.booking.Enum;

public enum PaymentStatusEnums {
    PENDING("Chờ thanh toán"),
    COMPLETED("Đã thanh toán"),
    FAILED("Thất bại"),
    CANCELLED("Đã hủy"),
    REFUNDED("Đã hoàn tiền"),
    EXPIRED("Hết hạn");

    private final String description;

    // Constructor cho enum
    PaymentStatusEnums(String description) {
        this.description = description;
    }

    // Getter cho mô tả trạng thái
    public String getDescription() {
        return description;
    }
}
