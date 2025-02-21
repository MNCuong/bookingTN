package com.example.booking.Enum;

public enum RoomStateEnums {
    AVAILABLE,          // Phòng trống, có thể đặt
    BOOKED,             // Đã có khách đặt nhưng chưa check-in
    OCCUPIED,           // Đang có khách ở
    CHECKED_OUT,        // Khách đã check-out, chờ dọn dẹp
    CLEANING,           // Đang được dọn dẹp
    UNDER_MAINTENANCE,  // Đang bảo trì, không thể đặt
    OUT_OF_SERVICE;     // Ngừng hoạt động (ví dụ: nâng cấp, hỏng hóc)

    @Override
    public String toString() {
        switch (this) {
            case AVAILABLE: return "Sẵn sàng";
            case BOOKED: return "Đã đặt";
            case OCCUPIED: return "Đang có khách";
            case CHECKED_OUT: return "Đã trả phòng";
            case CLEANING: return "Đang dọn dẹp";
            case UNDER_MAINTENANCE: return "Bảo trì";
            case OUT_OF_SERVICE: return "Tạm ngừng hoạt động";
            default: return super.toString();
        }
    }
}
