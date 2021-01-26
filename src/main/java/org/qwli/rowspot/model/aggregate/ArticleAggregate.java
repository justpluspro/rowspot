package org.qwli.rowspot.model.aggregate;

import org.qwli.rowspot.service.processor.MarkdownProcessor;
import org.qwli.rowspot.model.Article;

import java.io.Serializable;

public class ArticleAggregate implements Serializable {

    private String title;

    private String content;

    private Long comments;

    private Long id;

    public ArticleAggregate(Article article, MarkdownProcessor markdownProcessor, boolean isParse) {
        this.id = article.getId();
        if(isParse) {
            this.content = markdownProcessor.process(article.getContent());
        }
        this.title = article.getTitle();
        this.comments = article.getComments();
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
}
