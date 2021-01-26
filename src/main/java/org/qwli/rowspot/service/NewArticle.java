package org.qwli.rowspot.service;

import org.qwli.rowspot.model.enums.ArticleState;

import java.io.Serializable;

/**
 * 创建一个新的内容
 * @author liqiwen
 * @since 1.0
 */
public class NewArticle implements Serializable {

    /**
     * 创建标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 文章类型
     */
    private String articleType;

    /**
     * 是否允许评论
     */
    private boolean allowComment = true;

    /**
     * 文章状态
     */
    private ArticleState state;

    /**
     * 分类 id
     */
    private Long categoryId;

    /**
     * 菜单 id
     */
    private Long menuId;

    /**
     * 用户 id
     */
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public ArticleState getState() {
        return state;
    }

    public void setState(ArticleState state) {
        this.state = state;
    }

    public boolean isAllowComment() {
        return allowComment;
    }

    public void setAllowComment(boolean allowComment) {
        this.allowComment = allowComment;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
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
}
