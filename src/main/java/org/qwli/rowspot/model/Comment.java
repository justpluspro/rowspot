package org.qwli.rowspot.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author qwli7
 * @date 2021/1/27 13:17
 * 功能：Comment
 **/

@Entity(name = "Comment")
@Table(name = "comments", indexes = {
        @Index(name = "idx_article_id", columnList = "article_id")
})
public class Comment implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;

    /**
     * 评论内瓤
     */
    @Column(name = "content", length = 1024, nullable = false)
    private String content;

    /**
     * 父评论 id
     */
    @Column(name = "parent_id")
    private Long parentId;


    /**
     * 创建时间
     */
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;


    /**
     * 修改时间
     */
    @Column(name = "modify_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifyAt;

    /**
     * 是否审核
     */
    @Column(name = "checking", columnDefinition = "tinyint(1) default 1")
    private Boolean checking;


    /**
     * 发表用户 id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 关联文章 id
     */
    @Column(name = "article_id")
    private Long articleId;

    /**
     * 点赞
     */
    @Column(name = "vote_up_count")
    private Long voteUpCount;

    /**
     * 点踩
     */
    @Column(name = "vote_down_count")
    private Long voteDownCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Boolean getChecking() {
        return checking;
    }

    public void setChecking(Boolean checking) {
        this.checking = checking;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getVoteUpCount() {
        return voteUpCount;
    }

    public void setVoteUpCount(Long voteUpCount) {
        this.voteUpCount = voteUpCount;
    }

    public Long getVoteDownCount() {
        return voteDownCount;
    }

    public void setVoteDownCount(Long voteDownCount) {
        this.voteDownCount = voteDownCount;
    }
}
