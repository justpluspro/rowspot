package org.qwli.rowspot.model.enums;

public enum EditorType {

    MD("markdown");

    String code;

    EditorType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
