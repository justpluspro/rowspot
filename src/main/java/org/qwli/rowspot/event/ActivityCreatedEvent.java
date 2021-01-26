package org.qwli.rowspot.event;

import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.User;
import org.springframework.context.ApplicationEvent;


public class ActivityCreatedEvent extends ApplicationEvent {

    private final Article article;

    private final User user;

    public ActivityCreatedEvent(Object source, Article article, User user) {
        super(source);
        this.article = article;
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public User getUser() {
        return user;
    }
}
