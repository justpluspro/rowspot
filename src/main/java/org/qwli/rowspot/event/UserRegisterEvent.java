package org.qwli.rowspot.event;

import org.qwli.rowspot.model.User;
import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 * @author liqiwen
 * @since 1.2
 */
public class UserRegisterEvent extends ApplicationEvent {

    private final User user;


    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
