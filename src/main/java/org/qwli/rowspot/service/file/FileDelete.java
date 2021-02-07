package org.qwli.rowspot.service.file;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/7 15:06
 * 功能：FileDelete
 **/
public class FileDelete implements Serializable {

    /**
     * 操作的文件用户 id
     */
    private Long userId;

    /**
     * 操作的文件 id
     */
    private Long fileId;

    public FileDelete(Long userId, Long fileId) {
        this.userId = userId;
        this.fileId = fileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
