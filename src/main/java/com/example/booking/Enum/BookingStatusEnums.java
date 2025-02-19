package com.example.booking.Enum;

public enum BookingStatusEnums {
    PENDING,          // Đang chờ xử lý
    CONFIRMED,        // Đã xác nhận
    CHECKED_IN,       // Đã nhận phòng
    CHECKED_OUT,      // Đã trả phòng
    CANCELLED,        // Đã hủy
    NO_SHOW,          // Không đến
    FAILED,           // Thất bại
    EXPIRED;          // Hết hạn
}
