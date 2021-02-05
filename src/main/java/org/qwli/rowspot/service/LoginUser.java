package org.qwli.rowspot.service;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/1 18:22
 * 功能：LoginUser
 **/
public class LoginUser implements Serializable {

    private String email;

    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
