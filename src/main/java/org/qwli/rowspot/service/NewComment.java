package org.qwli.rowspot.service;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/1/27 13:39
 * 功能：NewComment
 **/
public class NewComment implements Serializable {

    private String content;

    private Long userId;

    private Long articleId;

    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
