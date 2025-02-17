//package com.example.booking.Service.Impl;
//
//import com.example.booking.Common.MessageCommon;
//import com.example.booking.Common.ServiceMessageConstants;
//import com.example.booking.DTO.Response.UploadFileResponse;
//import com.example.booking.Entity.UploadFile;
//import com.example.booking.Enum.FileTypeEnums;
//import com.example.booking.Exception.BookingException;
//import com.example.booking.Repository.UploadFileRepository;
//import com.example.booking.Service.MinIOService;
//import com.example.booking.Service.UploadFileService;
//import com.example.booking.Utils.FileUtil;
//import io.minio.CopyObjectArgs;
//import io.minio.CopySource;
//import io.minio.MinioClient;
//import io.minio.RemoveObjectArgs;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@AllArgsConstructor
//@Service
//public class UploadFileServiceImpl implements UploadFileService {
//
//    private final MinIOService minioService;
//    private final MinioClient minioClient;
//    private final UploadFileRepository uploadFileRepository;
//    //        private final ReconciliationService reconciliationService;
//    private final MessageCommon messageCommon;
//    //        private final InternalTransRequestService internalTransRequestService;
//    private static final DateTimeFormatter DATE_FORMATTER1 = DateTimeFormatter.ofPattern("yyyy-MM");
//    private static final DateTimeFormatter DATE_FORMATTER2 = DateTimeFormatter.ofPattern("dd_HH-mm-ss-SSS");
//
//
//    @Value("${minio.bucketName}")
//    private String bucketName;
//
//    @Override
//    public UploadFileResponse uploadToTmpFolder(MultipartFile file, FileTypeEnums type) {
//        validateFile(file, type);
//        String[] formatted = FileUtil.formatFileSize(file.getSize());
//        String tmpFilePathOrigin = buildTmpFilePath(file, type);
//        String tmpFilePath = tmpFilePathOrigin + "/" + file.getSize();
//        uploadFileToMinio(file, tmpFilePathOrigin);
//        String presignedUrl = generatePresignedUrl(tmpFilePathOrigin);
//
//        return UploadFileResponse.builder()
//                .filePath(tmpFilePath)
//                .presignedUrl(presignedUrl)
//                .fileName(file.getOriginalFilename())
//                .formattedSize(formatted[0] + formatted[1])
//                .build();
//    }
//
//    private void uploadFileToMinio(MultipartFile file, String tmpFilePath) {
//        try {
//            minioService.uploadFile(file, bucketName, tmpFilePath);
//        } catch (Exception e) {
//            throw new BookingException(ServiceMessageConstants.MINIO_UPLOAD_FAILED,
//                    messageCommon.getMessage(ServiceMessageConstants.MINIO_UPLOAD_FAILED), e);
//        }
//    }
//
//    private String generatePresignedUrl(String tmpFilePath) {
//        try {
//            return minioService.getPresignedUrl(tmpFilePath, bucketName);
//        } catch (Exception e) {
//            throw new BookingException(ServiceMessageConstants.MINIO_PRESIGNED_URL_FAILED,
//                    messageCommon.getMessage(ServiceMessageConstants.MINIO_PRESIGNED_URL_FAILED), e);
//        }
//    }
//
//    private String buildTmpFilePath(MultipartFile file, FileTypeEnums type) {
//        return "tmp/"
//                + LocalDateTime.now().format(DATE_FORMATTER1) + "/"
//                + type + "/"
//                + LocalDateTime.now().format(DATE_FORMATTER2) + "/"
//                + file.getOriginalFilename();
//    }
//
//    private void validateFile(MultipartFile file, FileTypeEnums type) {
//        final long MAX_FILE_SIZE = 25L * 1024 * 1024;
//
//        List<String> reconciliationFileExtensions = List.of("csv", "xlsx");
//        List<String> attachFileExtensions = List.of("png", "pdf", "jpg");
//
//        if (file.getSize() > MAX_FILE_SIZE) {
//            throw new BookingException(ServiceMessageConstants.FILE_SIZE_EXCEEDED,
//                    messageCommon.getMessage(ServiceMessageConstants.FILE_SIZE_EXCEEDED));
//        }
//
//        String originalFilename = file.getOriginalFilename();
//        if (originalFilename == null || !originalFilename.contains(".")) {
//            throw new BusinessException(ServiceMessageConstants.INVALID_FILE_TYPE,
//                    messageCommon.getMessage(ServiceMessageConstants.INVALID_FILE_TYPE));
//        }
//
//        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
//
//        if (type == FileTypeEnums.RECONCILIATION) {
//            if (!reconciliationFileExtensions.contains(fileExtension)) {
//                throw new BusinessException(ServiceMessageConstants.INVALID_FILE_TYPE,
//                        messageCommon.getMessage(ServiceMessageConstants.INVALID_FILE_TYPE));
//            }
//            // Validate file header for reconciliation type
//            validateFileHeader(file);
//        } else if (type == FileTypeEnums.ATTACH && !attachFileExtensions.contains(fileExtension)) {
//            throw new BusinessException(ServiceMessageConstants.INVALID_FILE_TYPE,
//                    messageCommon.getMessage(ServiceMessageConstants.INVALID_FILE_TYPE));
//        }
//    }
//
//    private void validateFileHeader(MultipartFile file) {
//        try (InputStream inputStream = file.getInputStream();
//             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//
//            String headerLine = reader.readLine();
//            if (headerLine == null || !headerLineMatchesTemplate(headerLine)) {
//                throw new BusinessException(ServiceMessageConstants.INVALID_FILE_HEADER,
//                        messageCommon.getMessage(ServiceMessageConstants.INVALID_FILE_HEADER));
//            }
//        } catch (IOException e) {
//            throw new BusinessException(ServiceMessageConstants.FILE_READ_ERROR,
//                    messageCommon.getMessage(ServiceMessageConstants.FILE_READ_ERROR), e);
//        }
//    }
//
//    private boolean headerLineMatchesTemplate(String headerLine) throws IOException {
//        try (InputStream templateStream = new ClassPathResource(FILE_PATH_CSV_TEMPLATE).getInputStream();
//             BufferedReader templateReader = new BufferedReader(new InputStreamReader(templateStream))) {
//
//            String templateHeader = templateReader.readLine();
//            if (templateHeader == null) {
//                throw new BusinessException(ServiceMessageConstants.TEMPLATE_NOT_FOUND,
//                        messageCommon.getMessage(ServiceMessageConstants.TEMPLATE_NOT_FOUND));
//            }
//            return List.of(headerLine.split(SEPERATOR)).equals(List.of(templateHeader.split(SEPERATOR)));
//        }
////    }
////
////    @Override
////    @Transactional
////    public List<String> saveFiles(Long requestId, List<String> tmpFilePaths, boolean isInternalTransRequest) {
////        boolean requestIdExists = isInternalTransRequest
////                ? internalTransRequestService.existsByRequestId(requestId)
////                : reconciliationService.existsByRequestId(requestId);
////        if (!requestIdExists) {
////            throw new BusinessException(ServiceMessageConstants.REQUEST_ID_NOT_FOUND,
////                    messageCommon.getMessage(ServiceMessageConstants.REQUEST_ID_NOT_FOUND));
////        }
////
////        List<UploadFileEntity> uploadFileEntities = new ArrayList<>();
////        List<CreateMoveFile> createMoveFiles = new ArrayList<>();
////        for (String tmpFilePath : tmpFilePaths) {
////            String[] pathParts = tmpFilePath.split("/");
////            if (pathParts.length < 6) {
////                throw new BusinessException(ServiceMessageConstants.INVALID_TMP_PATH,
////                        messageCommon.getMessage(ServiceMessageConstants.INVALID_TMP_PATH)
////                );
////            }
////            String yearMonth = pathParts[1];
////            String fileType = pathParts[2];
////            String timestampFolder = pathParts[3];
////            String fileName = pathParts[4];
////            Long size = Long.parseLong(pathParts[5]);
////
////            String newFilePath = String.format("permanent/%s/%d/%s/%s/%s",
////                    yearMonth,
////                    requestId,
////                    fileType,
////                    timestampFolder,
////                    fileName
////            );
////            UploadFileEntity uploadFileEntity = new UploadFileEntity();
////            uploadFileEntity.setPath(newFilePath);
////            uploadFileEntity.setFileName(fileName);
////            uploadFileEntity.setType(FileTypeEnums.valueOf(fileType));
////            if (isInternalTransRequest) {
////                uploadFileEntity.setInternalTransRequestId(requestId);
////            } else {
////                uploadFileEntity.setReconciliationId(requestId);
////            }
////            uploadFileEntity.setIsRead(false);
////            uploadFileEntity.setSize(size);
////            uploadFileEntity.setIsDeleted(0);
////            uploadFileEntities.add(uploadFileEntity);
////
////            CreateMoveFile createMoveFile = new CreateMoveFile();
////            createMoveFile.setNewFilePath(newFilePath);
////
////            String movePath = String.join("/", Arrays.copyOf(pathParts, pathParts.length - 1));
////            createMoveFile.setTmpFilePath(movePath);
////            createMoveFiles.add(createMoveFile);
////        }
////        try {
////            uploadFileRepository.saveAll(uploadFileEntities);
////        } catch (Exception e) {
////            throw new BusinessException(ServiceMessageConstants.FILE_SAVE_FAILED,
////                    messageCommon.getMessage(ServiceMessageConstants.FILE_SAVE_FAILED));
////        }
////
////        createMoveFiles.forEach(
////                value -> moveFile(bucketName, value.getTmpFilePath(), bucketName, value.getNewFilePath())
////        );
////        return createMoveFiles.stream().map(CreateMoveFile::getNewFilePath).collect(Collectors.toList());
////    }
//
////    @Override
////    public boolean deleteTempFiles(List<String> tmpFilePaths) {
////        try {
////            for (String tmpFilePath : tmpFilePaths) {
////                minioService.deleteFile(tmpFilePath, bucketName);
////            }
////            return true;
////        } catch (Exception e) {
////            throw new BusinessException(ServiceMessageConstants.FILE_DELETE_FAILED,
////                    messageCommon.getMessage(ServiceMessageConstants.FILE_DELETE_FAILED));
////        }
////    }
//
//    @Override
//    public void saveUploadFiles(List<UploadFile> uploadFileEntities) {
//        uploadFileRepository.saveAll(uploadFileEntities);
//    }
//
//    public void moveFile(String sourceBucket, String sourcePath, String targetBucket, String targetPath) {
//        try {
//            // Build the CopySource object
//            CopySource copySource = CopySource.builder()
//                    .bucket(sourceBucket)
//                    .object(sourcePath)
//                    .build();
//
//            minioClient.copyObject(
//                    CopyObjectArgs.builder()
//                            .bucket(targetBucket)
//                            .object(targetPath)
//                            .source(copySource)
//                            .build()
//            );
//
//            minioClient.removeObject(
//                    RemoveObjectArgs.builder()
//                            .bucket(sourceBucket)
//                            .object(sourcePath)
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new BookingException(ServiceMessageConstants.FILE_MOVE_FAILED,
//                    messageCommon.getMessage(ServiceMessageConstants.FILE_MOVE_FAILED));
//        }
//    }
//
////    @Transactional
////    public boolean moveBackFiles(List<String> permanentPaths) {
////        if (permanentPaths == null || permanentPaths.isEmpty()) {
////            throw new BookingException(ServiceMessageConstants.FILES_NOT_FOUND,
////                    messageCommon.getMessage(ServiceMessageConstants.FILES_NOT_FOUND));
////        }
//
////        List<CreateMoveFile> moveBackFiles = new ArrayList<>();
////        for (String permanentPath : permanentPaths) {
////            String[] pathParts = permanentPath.split("/");
////            if (pathParts.length < 6 || !("permanent").equals(pathParts[0])) {
////                throw new BusinessException(ServiceMessageConstants.INVALID_PERMANENT_PATH,
////                        messageCommon.getMessage(ServiceMessageConstants.INVALID_PERMANENT_PATH));
////            }
////
////            String tmpFilePath = String.format("tmp/%s/%s/%s/%s",
////                    pathParts[1],
////                    pathParts[3],
////                    pathParts[4],
////                    pathParts[5]
////            );
////
////            moveBackFiles.add(new CreateMoveFile(permanentPath, tmpFilePath));
////        }
////
////        try {
////            for (CreateMoveFile moveFile : moveBackFiles) {
////                moveFile(bucketName, moveFile.getTmpFilePath(), bucketName, moveFile.getNewFilePath());
////            }
////            return true;
////        } catch (Exception e) {
////            throw new BusinessException(ServiceMessageConstants.FILE_MOVE_BACK_FAILED,
////                    messageCommon.getMessage(ServiceMessageConstants.FILE_MOVE_BACK_FAILED));
////        }
////    }
////
////    @Override
////    public Page<UploadFileDto> getFileByReconciliationId(Long reconciliationId, FileTypeEnums type, Pageable pageable) {
////        Page<UploadFileDto> dtos = uploadFileRepository.findAllFileNameByType(reconciliationId, type, pageable);
////        List<UploadFileDto> updatedContent = dtos.getContent().stream().map(file -> {
////            file.setPresignedUrl(minioService.getPresignedUrl(file.getPath(), bucketName));
////            return file;
////        }).collect(Collectors.toList());
////
////        return new PageImpl<>(updatedContent, pageable, dtos.getTotalElements());
////    }
//
////    @Override
////    public DownloadFileResponse downloadFileById(Long id) {
////        UploadFileEntity fileEntity = uploadFileRepository.findById(id)
////                .orElseThrow(() -> new BusinessException(
////                        ServiceMessageConstants.FILE_NOT_FOUND,
////                        messageCommon.getMessage(ServiceMessageConstants.FILE_NOT_FOUND)
////                ));
////
////        ByteArrayInputStream fileStream;
////        try {
////            fileStream = minioService.getFile(fileEntity.getPath(), bucketName);
////        } catch (MinioException e) {
////            throw new BusinessException(
////                    ServiceMessageConstants.MINIO_FILE_NOT_FOUND,
////                    messageCommon.getMessage(ServiceMessageConstants.MINIO_FILE_NOT_FOUND)
////            );
////        }
////        return new DownloadFileResponse(fileEntity.getFileName(), new InputStreamResource(fileStream));
////    }
//
////    @Override
////    public DownloadFileResponse exportTemplate() {
////        ByteArrayInputStream fileStream;
////        try {
////            fileStream = minioService.getFile("TEMPLATE/TEMPLATE.csv", bucketName);
////        } catch (MinioException e) {
////            throw new BusinessException(
////                    ServiceMessageConstants.MINIO_FILE_NOT_FOUND,
////                    messageCommon.getMessage(ServiceMessageConstants.MINIO_FILE_NOT_FOUND)
////            );
////        }
////        return new DownloadFileResponse("TEMPLATE.csv", new InputStreamResource(fileStream));
////    }
////
////    @Override
////    public Page<UploadFileDto> getFileByInternalRequestId(Long internalRequestId, FileTypeEnums type, Pageable pageable) {
////        Page<UploadFileDto> dtos = uploadFileRepository.findAllFileNameByTypeAndInternalRequestId(internalRequestId, type, pageable);
////        List<UploadFileDto> updatedContent = dtos.getContent().stream().map(file -> {
////            file.setPresignedUrl(minioService.getPresignedUrl(file.getPath(), bucketName));
////            return file;
////        }).collect(Collectors.toList());
////
////        return new PageImpl<>(updatedContent, pageable, dtos.getTotalElements());
////    }
//}
//
