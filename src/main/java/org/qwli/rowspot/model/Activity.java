package org.qwli.rowspot.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.qwli.rowspot.model.enums.Action;
import org.qwli.rowspot.model.enums.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "Activity")
@Table(name = "activities")
public class Activity extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;


    @Column(name = "user_id")
    private Long userId;


    /**
     * 动作
     * 1. 收藏
     * 2. 提出
     * 3. 回答
     * 4. 赞
     * 5. 关注
     */
    @Column(name = "action")
    @Enumerated(value = EnumType.STRING)
    private Action action;


    @Column(name = "article_id")
    private Long articleId;


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

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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
