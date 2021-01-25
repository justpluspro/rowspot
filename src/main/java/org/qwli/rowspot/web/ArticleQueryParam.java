package org.qwli.rowspot.web;

import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.model.enums.ArticleState;

import java.io.Serializable;
import java.util.List;

public class ArticleQueryParam implements Serializable {

    private String userId;

    private Integer page;

    private ArticleType articleType;

    private List<ArticleState> states;

    public List<ArticleState> getStates() {
        return states;
    }

    public void setStates(List<ArticleState> states) {
        this.states = states;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
