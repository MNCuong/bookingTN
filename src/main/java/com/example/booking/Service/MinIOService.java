package com.example.booking.Service;

import io.minio.errors.MinioException;
import io.minio.messages.Item;

import java.io.InputStream;
import java.util.List;

public interface MinIOService {
    void uploadFile(InputStream fileStream, String fileName, String contentType, String hotelId, String roomType, Long roomId) throws MinioException;
    void uploadFileHotel(InputStream fileStream, String fileName, String contentType, String hotelId) throws MinioException;
    void uploadFileCar(InputStream fileStream, String fileName, String contentType, String hotelId,Long carId) throws MinioException;
    void uploadFileAriCraft(InputStream fileStream, String fileName, String contentType, String Registration) throws MinioException;
    void uploadFileAirline(InputStream fileStream, String fileName, String contentType, String code) throws MinioException;

    InputStream downloadFile(String fileName) throws MinioException;
    List<InputStream> downloadFileViewHotel(String hotelId) throws MinioException;

    // Lấy ảnh của một khách sạn
    List<String> getHotelImages(String hotelId);
    // Lấy ảnh của một phòng theo hotelId, roomType và roomId
    List<String> getRoomImages(String hotelId, String roomType, String roomId);
    List<String> getImagesByPrefix(String prefix);
    List<String> getImagesByCarId(String idCar);
    String getAirCraftImage(String registration);
    String getAirlineImage(String code);
}
