package com.example.booking.Service.Impl;

import com.example.booking.Service.MinIOService;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MinIOServiceImpl implements MinIOService {
    private static final Logger log = LoggerFactory.getLogger(MinIOServiceImpl.class);

    private final MinioClient minioClient;
    private final String bucketName = "booking";

    @Override
    public void uploadFile(InputStream fileStream, String fileName, String contentType, String hotelId, String roomType, Long roomId) throws MinioException {
        try {
            String newFileName = "Room" + "/" + hotelId + "/" + roomType + "/" + roomId + "/" + System.currentTimeMillis() + "_" + fileName;

            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName).build());
            }

            // Upload file lên MinIO
//            minioClient.putObject(bucketName, newFileName, fileStream, contentType);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(newFileName)
                            .stream(fileStream, fileStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

        } catch (Exception e) {
            log.error("Error uploading file to MinIO: {}", e.getMessage());
            throw new MinioException("Error uploading file to MinIO", e.getMessage());
        }
    }

    // Download file from MinIO
    @Override
    public InputStream downloadFile(String fileName) throws MinioException {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName).build());
        } catch (Exception e) {
            log.error("Error downloading file from MinIO: {}", e.getMessage());
            throw new MinioException("Error downloading file from MinIO", e.getMessage());
        }
    }

    @Override
    public List<InputStream> downloadFileViewHotel(String hotelId) throws MinioException {
        List<InputStream> fileStreams = new ArrayList<>();
        try {
            String prefix = "Hotel/" + hotelId + "/";

            Iterable<Result<Item>> objects = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .recursive(true)
                            .build());

            for (Result<Item> result : objects) {
                Item item = result.get();
                InputStream fileStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(item.objectName())
                                .build());
                fileStreams.add(fileStream);
            }
        } catch (Exception e) {
            log.error("Error getting files from MinIO: {}", e.getMessage());
            throw new MinioException("Error getting files from MinIO", e.getMessage());
        }
        return fileStreams;
    }

    @Override
    public List<String> getHotelImages(String hotelId) {
        return getImagesByPrefix("Hotel/" + hotelId + "/");
    }

    @Override
    public List<String> getRoomImages(String hotelId, String roomType, String roomId) {
        return getImagesByPrefix("Room/" + hotelId + "/" + roomType + "/" + roomId + "/");
    }


    @Override
    public void uploadFileHotel(InputStream fileStream, String fileName, String contentType, String hotelId) throws MinioException {
        try {
            String newFileName = "Hotel" + "/" + hotelId + "/" + System.currentTimeMillis() + "_" + fileName;

            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName).build());
            }

            // Upload file lên MinIO
//            minioClient.putObject(bucketName, newFileName, fileStream, contentType);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(newFileName)
                            .stream(fileStream, fileStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

        } catch (Exception e) {
            log.error("Error uploading file to MinIO: {}", e.getMessage());
            throw new MinioException("Error uploading file to MinIO", e.getMessage());
        }
    }

    @Override
    public void uploadFileCar(InputStream fileStream, String fileName, String contentType, String hotelId, Long carId) throws MinioException {
        try {
            String newFileName = "Car" + "/" + hotelId + "/" + carId + "/" + System.currentTimeMillis() + "_" + fileName;

            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName).build());
            }

            // Upload file lên MinIO
//            minioClient.putObject(bucketName, newFileName, fileStream, contentType);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(newFileName)
                            .stream(fileStream, fileStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );

        } catch (Exception e) {
            log.error("Error uploading file to MinIO: {}", e.getMessage());
            throw new MinioException("Error uploading file to MinIO", e.getMessage());
        }
    }

    //    hàm lấy ảnh
    @Override
    public List<String> getImagesByPrefix(String prefix) {
        List<String> imageUrls = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .recursive(true)//duyệt cả sub folder
                            .build()
            );

            for (Result<Item> result : results) {
                Item item = result.get();
                String url = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket(bucketName)
                                .object(item.objectName())
                                .expiry(60 * 60 * 24 * 7) // URL có hiệu lực 7 ngayf
                                .build()
                );
                imageUrls.add(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrls;
    }
    @Override
    public List<String> getImagesByCarId(String idCar) {
        List<String> imageUrls = new ArrayList<>();
        try {
            // Lấy tất cả ảnh trong thư mục Car/
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix("Car/")
                            .recursive(true) // Duyệt hết các folder con
                            .build()
            );

            // Duyệt qua từng file
            for (Result<Item> result : results) {
                Item item = result.get();
                String objectName = item.objectName();

                // Check nếu đường dẫn có chứa "/{idCar}/"
                if (!item.isDir() && objectName.matches("Car/\\d+/" + idCar + "/.*")) {
                    String url = minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(bucketName)
                                    .object(objectName)
                                    .expiry(60 * 60 * 24 * 7)
                                    .build()
                    );
                    imageUrls.add(url);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageUrls;
    }

}
