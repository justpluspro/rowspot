package org.qwli.rowspot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 默认用户配置
 * @author liqiwen
 * @since 1.2
 */
@Configuration
@ConfigurationProperties(prefix = "user.default")
public class DefaultUserProperties {

    /**
     * 默认头像
     */
    private String avatar;

    /**
     * 默认地址
     */
    private String address;

    /**
     * 默认 job
     */
    private String job;

    /**
     * 默认技能数
     */
    private String skills;

    /**
     * 默认网站
     */
    private String website;

    /**
     * 默认介绍
     */
    private String introduce;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
