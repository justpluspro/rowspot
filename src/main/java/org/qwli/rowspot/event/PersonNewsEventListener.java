package org.qwli.rowspot.event;


import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.PersonNews;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.enums.Action;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.repository.PersonNewsRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PersonNewsEventListener {

    private final PersonNewsRepository personNewsRepository;

    public PersonNewsEventListener(PersonNewsRepository personNewsRepository) {
        this.personNewsRepository = personNewsRepository;
    }


    @EventListener(PersonNewsCreatedEvent.class)
    public void handlePersonNews(PersonNewsCreatedEvent personNewsCreatedEvent) {

        final Article article = personNewsCreatedEvent.getArticle();
        final User user = personNewsCreatedEvent.getUser();

        PersonNews personNews = new PersonNews();
        personNews.setUserId(user.getId());
        personNews.setCreateAt(new Date());
        personNews.setModifyAt(new Date());

        if(article != null) {
            personNews.setArticleId(article.getId());
            final ArticleType articleType = article.getArticleType();
            if(articleType == ArticleType.Q) {
                //问题类型
                personNews.setAction(Action.PROPOSE);
            } else {
                //文章类型
                personNews.setAction(Action.PUBLISH);
            }
        } else {



        }

        personNewsRepository.save(personNews);

    }
}
