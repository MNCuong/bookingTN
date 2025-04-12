package com.example.booking.Common;

import org.springframework.stereotype.Component;

@Component
public class ServiceMessageConstants {
    public static final String EMAIL_EXIST = "ER-001";
    public static final String PHONE_EXIST = "ER-002";
    public static final String MINIO_UPLOAD_FAILED = "ER-003";
    public static final String MINIO_PRESIGNED_URL_FAILED = "ER-004";
    public static final String FILE_MOVE_FAILED = "ER-005";
    public static final String FILES_NOT_FOUND = "ER-006";
    public static final String FILE_SIZE_EXCEEDED = "ER-007";
    public static final String PRICE_INVALID = "ER-008";
    public static final String ID_ROOM_INVALID = "ER-009";
    public static final String ROOM_NOT_FOUND = "ER-010";
    public static final String USER_NOT_FOUND = "ER-011";
    public static final String USER_PROFILE_NOT_FOUND = "ER-012";
    public static final String HOTEL_NOT_FOUND = "ER-013";
    public static final String THIS_TIME_HAS_BEEN_BOOKED = "ER-014";
    public static final String TRANS_EXIST = "ER-015";
    public static final String USER_HAVE_NO_RIGHT = "ER-016";
    public static final String NOT_UPDATE_STATE_FLIGHT = "ER-017";
    public static final String INVALID_TOKEN = "ER-018";
    public static final String PAYMENT_FAILED = "ER-019";
    public static final String AIRPORT_NOT_FOUND = "ER-020";
    public static final String DEPARTURE_NOT_EMPTY = "ER-021";
    public static final String DIVISION_BY_ZERO = "ER-022";
    public static final String TO_LOCATION_NOT_EMPTY = "ER-023";
    public static final String NOT_ENOUGH_SEAT = "ER-024";
    public static final String SEAT_ALREADY_BOOKED = "ER-025";
    public static final String CHILD_ONLY_RECEIVES_TRUE_OR_FALSE = "ER-026";
    public static final String CREATE_FLIGHT_FAILD = "ER-027";
    public static final String CREATE_AIRCRAFT_FAILD = "ER-028";
    public static final String CHOOSE_DESTINATION_AND_PLACE = "ER-029";
    public static final String FULLNAME_AIRLINE_EXIST = "ER-030";
    public static final String FLIGHT_NOT_FOUND = "ER-031";
    public static final String FLIGHT_NOT_EXIST = "ER-032";

    public ServiceMessageConstants (){}
}
