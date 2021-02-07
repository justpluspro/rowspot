package org.qwli.rowspot.model.enums;

/**
 * @author qwli7
 * 文件类型
 */

public enum FileType {
    /**
     * JPG
     */
    JPG("jpg"),

    /**
     * JPEG
     */
    JPEG("jpeg"),

    /**
     * GIF
     */
    GIF("gif"),

    /**
     * WEBP
     */
    WEBP("webp"),

    /**
     * PNG
     */
    PNG("png"),

    /**
     * MP4
     */
    MP4("mp4"),

    /**
     * AVI
     */
    AVI("avi"),

    /**
     * RMVB
     */
    RMVB("rmvb"),

    /**
     * MOV
     */
    MOV("mov"),
    ;

    private final String code;

    FileType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
