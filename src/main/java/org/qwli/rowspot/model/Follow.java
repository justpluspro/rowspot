package org.qwli.rowspot.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * 关注表
 * @author liqiwen
 * @since 1.0
 */
@Entity(name = "Follow")
@Table(name = "follow", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_follow_user_id", columnList = "follow_user_id")
})
public class Follow implements Serializable {


    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "follow_user_id")
    private Long followUserId;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;

    @Column(name = "modify_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifyAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
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
