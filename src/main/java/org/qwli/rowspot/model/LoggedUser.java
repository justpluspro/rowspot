package org.qwli.rowspot.model;

import java.io.Serializable;

/**
 * 已登录的用户
 * @author liqiwen
 * @since 1.2
 */
public class LoggedUser implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * Email
     */
    private String email;


    public LoggedUser() {
        super();
    }

    public LoggedUser(Long id, String username, String email) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
