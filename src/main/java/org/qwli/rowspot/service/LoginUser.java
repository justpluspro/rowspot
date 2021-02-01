package org.qwli.rowspot.service;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/1 18:22
 * 功能：LoginUser
 **/
public class LoginUser implements Serializable {

    @NotNull
    @Length(min = 6, max = 16, message = "mail length is 6~12")
    private String mail;


    @NotNull
    @Length(min = 6, max = 32, message = "password length is 6~12")
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
