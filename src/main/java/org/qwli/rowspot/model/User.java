package org.qwli.rowspot.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.qwli.rowspot.util.Md5Util;
import org.qwli.rowspot.model.enums.BaseEntity;
import org.qwli.rowspot.model.enums.UserState;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @author liqiwen
 */
@Entity(name = "User")
@Table(name = "users", indexes = {
        @Index(name = "idx_username", columnList = "username"),
        @Index(name = "idx_email", columnList = "email", unique = true)
})
public class User extends BaseEntity implements Serializable {

    /**
     * 用户 id
     */
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;

    /**
     * 用户昵称
     */
    @Column(name = "username", unique = true, nullable = false, length = 64)
    private String username;

    /**
     * 用户邮箱
     */
    @Column(name = "email", unique = true, nullable = false, length = 64)
    private String email;


    @Column(name = "avatar", length = 256)
    private String avatar;

    /**
     * 用户密码
     */
    @Column(name = "password", nullable = false, length = 128)
    @ColumnDefault(value = "123456")
    private String password;

    /**
     * 用户状态
     */
    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault(value = "1")
    private UserState state = UserState.NORMAL;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_at")
    @Temporal(TemporalType.DATE)
    private Date lastLoginAt;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createAt;

    /**
     * 修改时间
     */
    @Column(name = "modify_at")
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date modifyAt;



    public User() {

    }

    public User(Long id) {
        this.id = id;
    }

    public User(String username, String email, String password) {
        validateEmail(email);
        validateUsername(username);
        this.password = Md5Util.md5(password);
        this.createAt = new Date();
        this.modifyAt = new Date();
        this.avatar = "";
    }




    private void validateUsername(String username) {
        if(StringUtils.isEmpty(username)) {
            throw new IllegalArgumentException("Empty username");
        }
        this.username = username;
    }

    private void validateEmail(String email) {
        if(StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException("Empty email");
        }
        this.email = email;
    }




    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
}
