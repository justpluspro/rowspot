package org.qwli.rowspot.event;


import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.Activity;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.enums.Action;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.repository.ActivitiesRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ActivityEventListener {

    private final ActivitiesRepository activitiesRepository;

    public ActivityEventListener(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }


    @EventListener(ActivityCreatedEvent.class)
    public void handlePersonNews(ActivityCreatedEvent activityCreatedEvent) {

        final Article article = activityCreatedEvent.getArticle();
        final User user = activityCreatedEvent.getUser();

        Activity activity = new Activity();
        activity.setUserId(user.getId());
        activity.setCreateAt(new Date());
        activity.setModifyAt(new Date());

        if(article != null) {
            activity.setArticleId(article.getId());
            final ArticleType articleType = article.getArticleType();
            if(articleType == ArticleType.Q) {
                //问题类型
                activity.setAction(Action.PROPOSE);
            } else {
                //文章类型
                activity.setAction(Action.PUBLISH);
            }
        } else {



        }

        activitiesRepository.save(activity);

    }
}
