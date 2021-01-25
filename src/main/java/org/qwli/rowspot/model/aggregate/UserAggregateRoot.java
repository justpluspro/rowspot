package org.qwli.rowspot.model.aggregate;

import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserAddition;

import java.io.Serializable;

public class UserAggregateRoot implements Serializable {

    private Long userId;

    private String username;

    private String email;

    private String introduces;

    private String job;

    private String skills;

    private String website;

    private String address;

    private String avatar;

    public UserAggregateRoot(User user, UserAddition userAddition) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();

        this.introduces = userAddition.getIntroduce();
        this.skills = userAddition.getSkills();
        this.job = userAddition.getJob();
        this.website = userAddition.getWebsite();
        this.address = userAddition.getAddress();
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

    public String getIntroduces() {
        return introduces;
    }

    public void setIntroduces(String introduces) {
        this.introduces = introduces;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
