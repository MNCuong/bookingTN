package com.example.booking.Config;


import java.io.Serializable;

public class ResponseDto<T> implements Serializable {
    private String code;
    private String message;
    private T data;

    public static <T> ResponseDtoBuilder<T> builder() {
        return new ResponseDtoBuilder<T>();
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public ResponseDto() {
    }

    public ResponseDto(final String code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static class ResponseDtoBuilder<T> {
        private String code;
        private String message;
        private T data;

        ResponseDtoBuilder() {
        }

        public ResponseDtoBuilder<T> code(final String code) {
            this.code = code;
            return this;
        }

        public ResponseDtoBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }

        public ResponseDtoBuilder<T> data(final T data) {
            this.data = data;
            return this;
        }

        public ResponseDto<T> build() {
            return new ResponseDto<T>(this.code, this.message, this.data);
        }

        public String toString() {
            return "ResponseDto.ResponseDtoBuilder(code=" + this.code + ", message=" + this.message + ", data=" + this.data + ")";
        }
    }
}
