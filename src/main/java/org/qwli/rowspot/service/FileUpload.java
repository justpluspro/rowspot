package org.qwli.rowspot.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author qwli7
 * 上传的文件
 */
public class FileUpload implements Serializable {

    /**
     * 上传的文件
     */
    private MultipartFile file;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 上传的用户
     */
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
