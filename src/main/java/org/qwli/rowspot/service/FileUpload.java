package org.qwli.rowspot.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class FileUpload implements Serializable {

    private MultipartFile file;

    private String filename;

    private Long userId;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
