package org.qwli.rowspot.model.enums;

import org.springframework.util.Assert;

public enum FileType {
    JPG("jpg"),
    JPEG("jpeg"),
    GIF("gif"),
    WEBP("webp"),
    PNG("png"),
    MP4("mp4"),
    MOV("mov"),
    ;

    private final String code;

    FileType(String code) {
        this.code = code;
    }

    public static FileType getTypeType(String extension) {
        Assert.notNull(extension, "extension not null.");
        final FileType[] values = FileType.values();
        for(FileType fileType: values) {
            if(fileType.getCode().equals(extension)){
                return fileType;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }
}
