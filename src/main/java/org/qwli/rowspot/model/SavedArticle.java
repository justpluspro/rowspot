package org.qwli.rowspot.model;

import org.qwli.rowspot.model.enums.ArticleState;

import java.io.Serializable;

/**
 * 已保存的文章
 * @author liqiwen
 * @since 1.2
 */
public class SavedArticle implements Serializable {

    /**
     * 保存的文章 id
     */
    private Long id;

    /**
     * 保存的状态
     */
    private ArticleState state;

    /**
     * 是否在审核
     */
    private Boolean checking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArticleState getState() {
        return state;
    }

    public void setState(ArticleState state) {
        this.state = state;
    }

    public Boolean getChecking() {
        return checking;
    }

    public void setChecking(Boolean checking) {
        this.checking = checking;
    }
}
