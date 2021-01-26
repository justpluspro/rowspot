package org.qwli.rowspot.event;

import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.repository.ArticleRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 文章事件监听
 * @author liqiwen
 * @since 1.2
 */
@Component
public class ArticleEventListener {

    /**
     * ArticleRepository
     */
    private final ArticleRepository articleRepository;

    public ArticleEventListener(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * 文章创建事件
     * @param event event
     */
    @EventListener(classes = ArticleCreatedEvent.class)
    public void processArticleCreatedEvent(ArticleCreatedEvent event) {
        Assert.notNull(event, "ArticleCreatedEvent not null.");

        final Article article = event.getArticle();


    }
}
