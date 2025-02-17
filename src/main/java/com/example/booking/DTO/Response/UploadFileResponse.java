package com.example.booking.DTO.Response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class UploadFileResponse {
    private String filePath;
    private String presignedUrl;
    private String fileName;
    private String formattedSize;
}
