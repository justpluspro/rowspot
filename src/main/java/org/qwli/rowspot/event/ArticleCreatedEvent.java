package org.qwli.rowspot.event;

import org.qwli.rowspot.model.Article;
import org.springframework.context.ApplicationEvent;

public class ArticleCreatedEvent extends ApplicationEvent {


    private final Article article;

    public ArticleCreatedEvent(Object source, Article article) {
        super(source);
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }
}
