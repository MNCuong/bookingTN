package com.example.booking.Config;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;

public class ResponseConfig<T> {
    public static final String SUCCESS_CODE = "00";

    public ResponseConfig() {
    }

    public static <T> ResponseEntity<ResponseDto<T>> success(T body) {
        ResponseDto responseDto = ResponseDto.builder().data(body).code("200").build();
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    public static ResponseEntity error(HttpStatus httpStatus, String errorCode, String message) {
        ResponseDto responseData = ResponseDto.builder().code(errorCode).message(message).build();
        return new ResponseEntity(responseData, httpStatus);
    }
    public static ResponseEntity error( String message) {
        ResponseDto responseData = ResponseDto.builder().message(message).build();
        return new ResponseEntity(responseData, HttpStatus.NOT_FOUND);
    }
    public static <T> ResponseEntity<T> error(HttpStatus httpStatus, T content, String code) {
        ResponseDto responseData = ResponseDto.builder().code(code).data(content).build();
        return new ResponseEntity(responseData, httpStatus);
    }

    public static ResponseEntity<InputStreamResource> downloadFile(String fileName, InputStreamResource input) {
        return ((ResponseEntity.BodyBuilder)((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[]{"attachment;filename=" + fileName})).header("Access-Control-Expose-Headers", new String[]{"Content-Disposition"})).contentType(MediaType.APPLICATION_OCTET_STREAM).body(input);
    }

    public static ResponseEntity<InputStreamResource> downloadFile(String fileName, ByteArrayInputStream outputStream) {
        return ((ResponseEntity.BodyBuilder)((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[]{"attachment;filename=" + fileName})).header("Access-Control-Expose-Headers", new String[]{"Content-Disposition"})).contentType(MediaType.APPLICATION_OCTET_STREAM).body(new InputStreamResource(outputStream));
    }
}
