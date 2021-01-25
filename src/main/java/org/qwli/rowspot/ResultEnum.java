package org.qwli.rowspot;

/**
 * @author qwli7
 * 返回结果枚举
 */

public enum ResultEnum {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),

    /**
     * 失败
     */
    FAILED(-1, "失败"),
    ;

    int code;
    String msg;

    ResultEnum(int code, String msg){
        this.code= code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
