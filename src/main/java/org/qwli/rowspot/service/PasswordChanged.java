package org.qwli.rowspot.service;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/5 8:33
 * 功能：PasswordChanged
 **/
public class PasswordChanged implements Serializable {
    /**
     * 用户 id
     */
    private Long id;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
