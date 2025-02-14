package com.example.booking.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;

public class BookingException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(BookingException.class);
    private String code;
    private Serializable errorData;

    public BookingException() {
        this((String) null);
    }

    public BookingException(String code) {
        this(code, code);
    }

    public BookingException(String code, String message) {
        this(code, message, (Throwable) null);
    }

    public BookingException(String code, String message, Throwable cause) {
        this(code, message, (Serializable) null, cause);
    }

    public BookingException(String code, String message, Serializable errorData, Throwable cause) {
        super(message, cause);
        this.code = code;
        if (errorData != null) {
            this.errorData = errorData;
        } else if (message != null) {
            if (logger.isDebugEnabled()) {
                this.errorData = new HashMap(Collections.singletonMap("messsage", message));
            } else {
                this.errorData = new HashMap();
            }
        }

        logger.error(String.format("SocialException(code=[%s], message=[%s])", code, message), cause);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Serializable getErrorData() {
        return this.errorData;
    }

    public void setErrorData(Serializable errorData) {
        this.errorData = errorData;
    }
}


