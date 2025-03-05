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
        return switch (this) {
            case AVAILABLE -> "Sẵn sàng";
            case BOOKED -> "Đã đặt";
            case OCCUPIED -> "Đang có khách";
            case CHECKED_OUT -> "Đã trả phòng";
            case CLEANING -> "Đang dọn dẹp";
            case UNDER_MAINTENANCE -> "Bảo trì";
            case OUT_OF_SERVICE -> "Tạm ngừng hoạt động";
            default -> super.toString();
        };
    }
}
