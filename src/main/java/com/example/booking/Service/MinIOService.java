package com.example.booking.Service;

import io.minio.errors.MinioException;
import io.minio.messages.Item;

import java.io.InputStream;
import java.util.List;

public interface MinIOService {
    void uploadFile(InputStream fileStream, String fileName, String contentType, String hotelId, String roomType, Long roomId) throws MinioException;
    void uploadFileHotel(InputStream fileStream, String fileName, String contentType, String hotelId) throws MinioException;

    InputStream downloadFile(String fileName) throws MinioException;
    List<InputStream> downloadFileViewHotel(String hotelId) throws MinioException;

    List<Item> listFiles() throws MinioException;

}
