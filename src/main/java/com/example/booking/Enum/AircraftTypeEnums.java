package com.example.booking.Enum;


import lombok.Getter;

@Getter
public enum AircraftTypeEnums {
    AIRBUS_A320("Airbus A320", 180),
    AIRBUS_A321("Airbus A321", 230),
    BOEING_737_800("Boeing 737-800", 189),
    BOEING_737_MAX_8("Boeing 737 MAX 8", 200),
    BOEING_787_9("Boeing 787-9", 296),
    AIRBUS_A350_900("Airbus A350-900", 305),
    BOEING_777_300ER("Boeing 777-300ER", 368),
    AIRBUS_A380("Airbus A380", 850);

    private final String model;
    private final int seatCapacity;

    AircraftTypeEnums(String model, int seatCapacity) {
        this.model = model;
        this.seatCapacity = seatCapacity;
    }

    public static int getSeatsByModel(String model) {
        for (AircraftTypeEnums type : values()) {
            if (type.getModel().equalsIgnoreCase(model)) {
                return type.getSeatCapacity();
            }
        }
        return 180; // Mặc định nếu không tìm thấy
    }
}
