package org.qwli.rowspot.event;

import org.qwli.rowspot.model.User;

/**
 * @author liqiwen
 * @since 1.2
 * 关注事件
 */
public class FollowedEvent extends AbstractEvent {

    private final User user;

    private final User followUser;

    public FollowedEvent(Object source, User user, User followUser) {
        super(source, EventType.FOLLOWED);
        this.user = user;
        this.followUser = followUser;
    }

    public User getUser() {
        return user;
    }

    public User getFollowUser() {
        return followUser;
    }
}

