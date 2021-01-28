package org.qwli.rowspot.web;

import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.model.enums.ArticleState;

import java.io.Serializable;

/**
 * @author qwli7
 * 查询参数
 */
public class ArticleQueryParam extends AbstractQueryParam implements Serializable {

    private Long userId;

    private ArticleType articleType;

    private ArticleState articleState;

    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public ArticleState getArticleState() {
        return articleState;
    }

    public void setArticleState(ArticleState articleState) {
        this.articleState = articleState;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
