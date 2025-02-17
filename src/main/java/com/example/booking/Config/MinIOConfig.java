package com.example.booking.Config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOConfig {
    @Value("${minio.endPoint}")
    private String endPoint;

    @Value("${minio.accessKey}")
    private String encodedAccessKey;

    @Value("${minio.secretKey}")
    private String encodedSecretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        String accessKey = new String(encodedAccessKey);
        String secretKey = new String(encodedSecretKey);
        return MinioClient.builder()
                .endpoint(endPoint)
                .credentials(accessKey, secretKey)
                .build();
    }

}
