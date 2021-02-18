package org.qwli.rowspot.event;


import org.qwli.rowspot.model.User;
import org.qwli.rowspot.repository.ActivitiesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 事件处理器
 * @author liqiwen
 * @since 1.2
 */
@Component
public class EventListenProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    private final ActivitiesRepository activitiesRepository;

    public EventListenProcessor(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }



    @EventListener(classes = {FollowedEvent.class, UnFollowEvent.class})
    public void processEvent(AbstractEvent abstractEvent) {
        final EventType eventType = abstractEvent.getEventType();
        switch (eventType) {
            case FOLLOWED:
                logger.info("processEvent followed event:[{}]", abstractEvent);
                FollowedEvent followedEvent = (FollowedEvent) abstractEvent;
                processFollowedEvent(followedEvent);
                break;
            case UN_FOLLOWED:
                logger.info("processEvent unfollowed event:[{}]", abstractEvent);
                UnFollowEvent unFollowEvent = (UnFollowEvent) abstractEvent;
                processUnfollowEvent(unFollowEvent);
                break;
        }
    }

    /**
     * 处理取消关注事件
     * @param unFollowEvent unFollowEvent
     */
    private void processUnfollowEvent(UnFollowEvent unFollowEvent) {
        final EventType eventType = unFollowEvent.getEventType();
        final long timestamp = unFollowEvent.getTimestamp();
    }


    /**
     * 处理关注事件
     * @param followedEvent followedEvent
     */
    public void processFollowedEvent(FollowedEvent followedEvent) {
        final User followUser = followedEvent.getFollowUser();
        final User user = followedEvent.getUser();

    }
}
