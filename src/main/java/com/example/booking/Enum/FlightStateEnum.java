package com.example.booking.Enum;

import java.util.EnumSet;
import java.util.Set;

public enum FlightStateEnum {
    SCHEDULED,      // Đã lên lịch
    ON_TIME,        // Đúng giờ
    DELAYED,        // Bị trễ
    BOARDING,       // Đang lên máy bay
    IN_FLIGHT,      // Đang bay
    LANDED,         // Đã hạ cánh
    CANCELLED;     // Bị hủy

    // Các trạng thái có thể cập nhật từ mỗi trạng thái
    private static final Set<FlightStateEnum> FINAL_STATES = EnumSet.of(LANDED, CANCELLED);

    private static final Set<FlightStateEnum> SCHEDULED_ALLOWED = EnumSet.of(ON_TIME, DELAYED, CANCELLED);
    private static final Set<FlightStateEnum> ON_TIME_ALLOWED = EnumSet.of(DELAYED, BOARDING, CANCELLED);
    private static final Set<FlightStateEnum> DELAYED_ALLOWED = EnumSet.of(ON_TIME, BOARDING, CANCELLED);
    private static final Set<FlightStateEnum> BOARDING_ALLOWED = EnumSet.of(IN_FLIGHT, CANCELLED);
    private static final Set<FlightStateEnum> IN_FLIGHT_ALLOWED = EnumSet.of(LANDED);

    public boolean canTransitionTo(FlightStateEnum newStatus) {
        if (FINAL_STATES.contains(this)) {
            return false; // Không thể cập nhật từ trạng thái cuối
        }

        return switch (this) {
            case SCHEDULED -> SCHEDULED_ALLOWED.contains(newStatus);
            case ON_TIME -> ON_TIME_ALLOWED.contains(newStatus);
            case DELAYED -> DELAYED_ALLOWED.contains(newStatus);
            case BOARDING -> BOARDING_ALLOWED.contains(newStatus);
            case IN_FLIGHT -> IN_FLIGHT_ALLOWED.contains(newStatus);
            default -> false;
        };
    }
}
