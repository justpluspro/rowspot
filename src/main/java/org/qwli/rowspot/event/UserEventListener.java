package org.qwli.rowspot.event;

import org.qwli.rowspot.config.DefaultUserProperties;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserAddition;
import org.qwli.rowspot.model.factory.UserFactory;
import org.qwli.rowspot.repository.UserAdditionRepository;
import org.qwli.rowspot.repository.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * 用户事件
 * @author liqiwen
 * @since 1.2
 */
@Component
public class UserEventListener {

    /**
     * userRepository
     */
    private final UserRepository userRepository;

    /**
     * UserAdditionRepository
     */
    private final UserAdditionRepository userAdditionRepository;

    /**
     * 默认用户配置
     */
    private final DefaultUserProperties defaultUserProperties;

    public UserEventListener(UserRepository userRepository,UserAdditionRepository userAdditionRepository,
                             DefaultUserProperties defaultUserProperties) {
        this.userRepository = userRepository;
        this.defaultUserProperties = defaultUserProperties;
        this.userAdditionRepository = userAdditionRepository;
    }

    @EventListener(classes = UserLoginEvent.class)
    public void processUserLoginEvent(UserLoginEvent event) {
        Assert.notNull(event, "UserLoginEvent not null.");
        final User user = event.getUser();
        userRepository.updateUserLoginDate(user.getId(), new Date());
    }

    @EventListener(classes = UserRegisterEvent.class)
    public void processUserRegisterEvent(UserRegisterEvent event) {
        Assert.notNull(event, "UserRegisterEvent not null.");
        final User user = event.getUser();
        UserAddition userAddition = UserFactory.createUserAddition(user, defaultUserProperties);
        userAdditionRepository.save(userAddition);
    }
}
