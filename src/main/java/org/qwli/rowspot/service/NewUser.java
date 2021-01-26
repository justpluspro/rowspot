package org.qwli.rowspot.service;

import java.io.Serializable;

/**
 * 新用户
 * @author liqiwen
 * @since 1.2
 */
public class NewUser implements Serializable {

    /**
     * 注册名称
     */
    private String registerName;

    /**
     * 注册 Email
     */
    private String registerEmail;

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getRegisterEmail() {
        return registerEmail;
    }

    public void setRegisterEmail(String registerEmail) {
        this.registerEmail = registerEmail;
    }
}
