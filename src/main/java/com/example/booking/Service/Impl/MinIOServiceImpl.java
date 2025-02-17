package com.example.booking.Service.Impl;

import com.example.booking.Exception.MinIOException;
import com.example.booking.Service.MinIOService;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;

import java.io.ByteArrayInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MinIOServiceImpl implements MinIOService {
    private static final Logger log = LoggerFactory.getLogger(MinIOServiceImpl.class);
    @Autowired
    private MinioClient minioClient;
    public static final String MINIO_ERROR = "MINIO_ERROR";

    public MinIOServiceImpl() {
    }

    public String getPresignedUrl(String fileName, String bucketName) {
        try {
            return this.minioClient.getPresignedObjectUrl((GetPresignedObjectUrlArgs) ((GetPresignedObjectUrlArgs.Builder) ((GetPresignedObjectUrlArgs.Builder) GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName)).object(fileName)).build());
        } catch (Exception exception) {
            throw new MinIOException("MINIO_ERROR", "getPresignedUrl error fileName = " + fileName + " , bucketName = " + bucketName, exception);
        }
    }

    public void deleteFile(String fileName, String bucketName) {
        try {
            this.minioClient.removeObject((RemoveObjectArgs) ((RemoveObjectArgs.Builder) ((RemoveObjectArgs.Builder) RemoveObjectArgs.builder().bucket(bucketName)).object(fileName)).build());
        } catch (Exception exception) {
            throw new MinIOException("MINIO_ERROR", "deleteFile error fileName = " + fileName + " , bucketName = " + bucketName, exception);
        }
    }

    public ByteArrayInputStream getInputStreamTemplate(String fileName, String bucketName) {
        try {
            GetObjectResponse object = this.minioClient.getObject((GetObjectArgs) ((GetObjectArgs.Builder) ((GetObjectArgs.Builder) GetObjectArgs.builder().bucket(bucketName)).object(fileName)).build());
            return new ByteArrayInputStream(object.readAllBytes());
        } catch (Exception exception) {
            throw new MinIOException("MINIO_ERROR", "getInputStreamTemplate error fileName = " + fileName + " , bucketName = " + bucketName, exception);
        }
    }

    public boolean bucketExists(String bucketName) {
        try {
            return this.minioClient.bucketExists((BucketExistsArgs) ((BucketExistsArgs.Builder) BucketExistsArgs.builder().bucket(bucketName)).build());
        } catch (Exception exception) {
            throw new MinIOException("MINIO_ERROR", "bucketExists error  bucketName = " + bucketName, exception);
        }
    }

    public void makeBucket(String bucketName) {
        try {
            this.minioClient.makeBucket((MakeBucketArgs) ((MakeBucketArgs.Builder) MakeBucketArgs.builder().bucket(bucketName)).build());
        } catch (Exception exception) {
            throw new MinIOException("MINIO_ERROR", "makeBucket error  bucketName = " + bucketName, exception);
        }
    }

    public ByteArrayInputStream getFile(String fileName, String bucketName) {
        try {
            if (this.bucketExists(bucketName)) {
                GetObjectResponse object = this.minioClient.getObject((GetObjectArgs) ((GetObjectArgs.Builder) ((GetObjectArgs.Builder) GetObjectArgs.builder().bucket(bucketName)).object(fileName)).build());
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(object.readAllBytes());
                return byteArrayInputStream;
            } else {
                return null;
            }
        } catch (Exception exception) {
            throw new MinIOException("MINIO_ERROR", "getFile error fileName = " + fileName + " , bucketName = " + bucketName, exception);
        }
    }

    public String uploadFile(MultipartFile file, String bucketName, String tmpName) {
        if (!this.bucketExists(bucketName)) {
            this.makeBucket(bucketName);
        }

        try {
            this.minioClient.putObject((PutObjectArgs) ((PutObjectArgs.Builder) ((PutObjectArgs.Builder) PutObjectArgs.builder().bucket(bucketName)).object(tmpName)).stream(file.getInputStream(), file.getSize(), -1L).contentType(file.getContentType()).build());
            return "Done";
        } catch (Exception exception) {
            throw new MinIOException("MINIO_ERROR", "uploadFile error bucketName = " + bucketName, exception);
        }
    }
}

