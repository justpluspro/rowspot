package org.qwli.rowspot.web;

import java.io.Serializable;

/**
 * 评论查询参数
 * @author liqiwen
 */
public class CommentQueryParam extends AbstractQueryParam implements Serializable {

    private Long articleId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
