package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class File {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private String fileData;

    public File(Integer fileId, String fileName, String contentType, String fileSize, Integer userId, String fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }
}
