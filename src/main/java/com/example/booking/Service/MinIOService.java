package com.example.booking.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface MinIOService {
    String getPresignedUrl(String fileName, String bucketName);

    void deleteFile(String fileName, String bucketName);

    ByteArrayInputStream getInputStreamTemplate(String fileName, String bucketName);

    boolean bucketExists(String bucketName);

    void makeBucket(String bucketName);

    ByteArrayInputStream getFile(String fileName, String bucketName);

    String uploadFile(MultipartFile file, String bucketName, String tmpName);
}
