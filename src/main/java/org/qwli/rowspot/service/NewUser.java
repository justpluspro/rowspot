package org.qwli.rowspot.service;

import java.io.Serializable;

public class NewUser implements Serializable {

    private String registerName;

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
