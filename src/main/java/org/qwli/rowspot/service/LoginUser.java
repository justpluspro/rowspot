package org.qwli.rowspot.service;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/1 18:22
 * 功能：LoginUser
 **/
public class LoginUser implements Serializable {

    private String mail;

    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
