package com.example.booking.Entity;

import com.example.booking.Enum.FileTypeEnums;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "UPLOAD_FILES")
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 1000)
    @Column(name = "PATH", length = 1000)
    private String path;

    @Size(max = 1000)
    @Column(name = "FILE_NAME", length = 1000)
    private String fileName;

    @Enumerated(EnumType.STRING)
    private FileTypeEnums type;



    @Column(name = "IS_READ")
    private Boolean isRead;

    @Column(name = "FILE_SIZE")
    private Long size;
}
