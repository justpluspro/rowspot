package org.qwli.rowspot.web;

import org.qwli.rowspot.model.enums.ArticleType;

import java.io.Serializable;

public class HandledArticleQueryParam implements Serializable {

    private Long userId;

    private ArticleType articleType;


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
