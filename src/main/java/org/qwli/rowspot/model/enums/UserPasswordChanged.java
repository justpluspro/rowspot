package org.qwli.rowspot.model.enums;

import org.qwli.rowspot.model.User;
import org.springframework.context.ApplicationEvent;


public class UserPasswordChanged extends ApplicationEvent {


    private final User user;


    public UserPasswordChanged(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
