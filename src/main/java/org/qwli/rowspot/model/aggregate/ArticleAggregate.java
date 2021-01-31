package org.qwli.rowspot.model.aggregate;

import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.service.processor.MarkdownProcessor;
import org.qwli.rowspot.model.Article;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章内容聚合根
 * @author qwli7
 */
public class ArticleAggregate implements Serializable {

    /**
     * 文章 id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    /**
     * 评论数量
     */
    private Long comments;

    /**
     * 浏览量
     */
    private Long visits;

    /**
     * 是否已经解决
     */
    private Boolean solved = false;

    /**
     * 发布时间
     */
    private Date postedAt;


    private List<TypeAggregate> typeAggregates = new ArrayList<>();


    public List<TypeAggregate> getTypeAggregates() {
        return typeAggregates;
    }

    public void setTypeAggregates(List<TypeAggregate> typeAggregates) {
        this.typeAggregates = typeAggregates;
    }

    public ArticleAggregate(Article article, MarkdownProcessor markdownProcessor, boolean isParse) {
        this.id = article.getId();
        if(isParse || markdownProcessor != null) {
            this.content = markdownProcessor.process(article.getContent());
        } else {
            this.content = "";
        }
        this.title = article.getTitle();
        this.comments = article.getComments();
        ArticleType articleType = article.getArticleType();
        if(ArticleType.isQuestion(articleType.name())) {
            this.solved = article.getSolved();
        }
        this.visits = article.getVisits();
        this.postedAt = article.getPostAt();
    }

    public ArticleAggregate(Article article) {
        this(article, null, false);
    }


    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Date getPostedAt() {
        return postedAt;
    }

    public Boolean getSolved() {
        return solved;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }
}
