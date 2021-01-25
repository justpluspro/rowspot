package org.qwli.rowspot.event;

import org.qwli.rowspot.model.User;
import org.qwli.rowspot.repository.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserEventListener {


    private final UserRepository userRepository;

    public UserEventListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(classes = UserLoginEvent.class)
    public void handleUserLoginEvent(UserLoginEvent event) {
        final User user = event.getUser();

        userRepository.updateUserLoginDate(user.getId(), new Date());

    }
}
