package org.qwli.rowspot.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.qwli.rowspot.model.enums.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "user_additions",
        indexes = {
        @Index(name = "idx_user_id", unique = true,columnList = "user_id")
})
@Entity(name = "UserAddition")
public class UserAddition extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;

    @Column(name = "job", length = 64)
    private String job;


    @Column(name = "skills", length = 64)
    private String skills;


    @Column(name = "address", length = 64)
    private String address;


    @Column(name = "website", length = 64)
    private String website;

    @Column(name = "introduce", length = 1023)
    private String introduce;


    @Column(name = "user_id")
    private Long userId;


    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createAt;

    @Column(name = "modify_at")
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date modifyAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
