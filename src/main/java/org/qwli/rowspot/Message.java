package org.qwli.rowspot;

import org.springframework.validation.MessageCodesResolver;

import java.io.Serializable;

/**
 * 消息实体类
 * @author liqiwen
 */
public class Message implements MessageCodesResolver, Serializable {

    /**
     * 业务 message
     */
    private String msg;

    /**
     * 业务 code
     */
    private String code;

    public Message(String code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String[] resolveMessageCodes(String s, String s1) {
        return new String[0];
    }

    @Override
    public String[] resolveMessageCodes(String s, String s1, String s2, Class<?> aClass) {
        return new String[0];
    }
}
