package com.example.booking.Service;

import com.example.booking.DTO.Response.UploadFileResponse;
import com.example.booking.Entity.UploadFile;
import com.example.booking.Enum.FileTypeEnums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFileService {
    UploadFileResponse uploadToTmpFolder(MultipartFile file, FileTypeEnums type);

    List<String> saveFiles(Long requestId, List<String> tmpFilePaths, boolean isInternalTransRequest);

    boolean moveBackFiles(List<String> permanentPaths);

    boolean deleteTempFiles(List<String> tmpFilePaths);
    void saveUploadFiles(List<UploadFile> uploadFileEntities);

//    DownloadFileResponse downloadFileById(Long id) ;
//
//    Page<UploadFileDto> getFileByReconciliationId(Long reconciliationId, FileTypeEnums type, Pageable pageable);
//
//    DownloadFileResponse exportTemplate();

//    Page<UploadFileDto> getFileByInternalRequestId(Long internalRequestId, FileTypeEnums type, Pageable pageable);
}
