package org.qwli.rowspot.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.model.enums.BaseEntity;
import org.qwli.rowspot.service.NewArticle;
import org.qwli.rowspot.model.enums.ArticleState;
import org.qwli.rowspot.model.enums.EditorType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "Article")
@Table(name = "articles")
public class Article extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;


    @Column(name = "title", nullable = false)
    private String title;


    @Lob
    @Column(name = "content")
    private String content;


    @Lob
    @Column(name = "summary")
    private String summary;


    @Column(name = "index_unique", columnDefinition = "tinyint(1) default 1")
    private Boolean indexUnique;

    @Column(name = "solved", columnDefinition = "tinyint(1) default 1")
    private Boolean solved;


    @Column(name = "click_count", nullable = false)
    @ColumnDefault(value = "0")
    private Long visits;


    @Column(name = "like_count", nullable = false)
    @ColumnDefault(value = "0")
    private Long likes;


    @Column(name = "subscribe_count", nullable = false)
    @ColumnDefault(value = "0")
    private Long votes;


    @Column(name = "comment_count")
    @ColumnDefault(value = "0")
    private Long comments;


    @Column(name = "allow_comment", columnDefinition = "tinyint(1) default 1")
    private Boolean allowComment;


    @Column(name = "state")
    @ColumnDefault(value = "0")
    @Enumerated(value = EnumType.ORDINAL)
    private ArticleState state;


    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createAt;


    @Column(name = "modify_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifyAt;


    @Column(name = "post_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postAt;


    @Column(name = "editor_type")
    @Enumerated(value = EnumType.ORDINAL)
    @ColumnDefault(value = "0")
    private EditorType editorType;


    @Column(name = "article_type")
    @Enumerated(value = EnumType.STRING)
    private ArticleType articleType;


    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "menu_id")
    private Long menuId;


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Article() {

    }

    public Article(NewArticle newArticle, Category category) {
        this.userId = newArticle.getUserId();
        this.title = newArticle.getTitle();
        this.content = newArticle.getContent();
        this.createAt = new Date();
        this.modifyAt = new Date();

        this.allowComment = newArticle.isAllowComment();
        this.comments = 0L;
        this.likes = 0L;
        this.visits = 0L;
        this.votes = 0L;
        this.editorType = EditorType.MD;
        this.categoryId = category.getId();

        final String articleType = newArticle.getArticleType();
        for(ArticleType value: ArticleType.values()) {
            final String name = value.name();
            if(name.equals(articleType)) {
                this.articleType = value;
                this.indexUnique = ArticleType.isIndexUnique(name);
                break;
            }
        }

        if(ArticleType.isArticle(articleType)) {
            this.menuId = newArticle.getMenuId();
        }

        this.solved = false;
        this.state = ArticleState.POSTED;
        this.postAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getIndexUnique() {
        return indexUnique;
    }

    public void setIndexUnique(Boolean indexUnique) {
        this.indexUnique = indexUnique;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public ArticleState getState() {
        return state;
    }

    public void setState(ArticleState state) {
        this.state = state;
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

    public Date getPostAt() {
        return postAt;
    }

    public void setPostAt(Date postAt) {
        this.postAt = postAt;
    }

    public EditorType getEditorType() {
        return editorType;
    }

    public void setEditorType(EditorType editorType) {
        this.editorType = editorType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }
}
