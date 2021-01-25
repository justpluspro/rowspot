package org.qwli.rowspot.event;

import org.qwli.rowspot.model.User;
import org.springframework.context.ApplicationEvent;

/**
 * UserLoginEvent
 */
public class UserLoginEvent extends ApplicationEvent {

    private final User user;

    public UserLoginEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
