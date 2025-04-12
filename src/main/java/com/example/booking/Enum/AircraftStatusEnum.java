package com.example.booking.Enum;


public enum AircraftStatusEnum {
    ACTIVE,               // Máy bay đang hoạt động bình thường
    IN_SERVICE,           // Máy bay đang trong lịch trình bay
    STANDBY,              // Máy bay sẵn sàng nhưng chưa sử dụng ngay
    ON_GROUND,            // Máy bay đang ở sân bay

    UNDER_MAINTENANCE,    // Đang bảo trì, kiểm tra định kỳ
    REPAIRING,            // Đang sửa chữa do sự cố kỹ thuật
    OUT_OF_SERVICE,       // Không thể hoạt động do lỗi nghiêm trọng
    AOG,                  // (Aircraft on Ground) Máy bay hỏng nặng, cần sửa chữa

    RETIRED,              // Đã ngừng hoạt động vĩnh viễn
    STORED,               // Đang được bảo quản, không sử dụng
    SCRAPPED,             // Đã bị tháo dỡ hoặc phá hủy
    DELAYED               // Bị hoãn do sự cố kỹ thuật hoặc thời tiết
}
