package org.qwli.rowspot.service.file;

import org.qwli.rowspot.model.enums.FileType;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/7 15:22
 * 功能：FileInfoDetail
 **/
public class FileInfoDetail implements Serializable {

    private long size;

    private String humanCanRead;

    private FileType fileType;

    private String url;

    private long width;

    private long height;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getHumanCanRead() {
        return humanCanRead;
    }

    public void setHumanCanRead(String humanCanRead) {
        this.humanCanRead = humanCanRead;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }
}
