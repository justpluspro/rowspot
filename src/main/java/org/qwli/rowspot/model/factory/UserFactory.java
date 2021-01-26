package org.qwli.rowspot.model.factory;

import org.qwli.rowspot.config.DefaultUserProperties;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserAddition;
import org.qwli.rowspot.model.enums.UserState;
import org.qwli.rowspot.service.NewUser;

import java.util.Date;

/**
 * 用户工厂
 * @author liqiwen
 * @since 1.2
 */
public class UserFactory {

    /**
     * 创建用户
     * @param newUser newUser
     * @return User
     */
    public static User createUser(NewUser newUser, DefaultUserProperties defaultUserProperties) {
        User user = new User();
        user.setUsername(newUser.getRegisterName());
        user.setEmail(newUser.getRegisterEmail());

        user.setCreateAt(new Date());
        user.setModifyAt(new Date());
        user.setAvatar(defaultUserProperties.getAvatar());
        user.setState(UserState.UNACTIVATED);

        return user;
    }

    /**
     * 创建用户介绍
     * @param user user
     * @return UserAddition
     */
    public static UserAddition createUserAddition(User user, DefaultUserProperties defaultUserProperties) {
        UserAddition userAddition = new UserAddition();
        userAddition.setUserId(user.getId());
        userAddition.setAddress(defaultUserProperties.getAddress());
        userAddition.setJob(defaultUserProperties.getJob());
        userAddition.setSkills(defaultUserProperties.getSkills());
        userAddition.setWebsite(defaultUserProperties.getWebsite());
        userAddition.setIntroduce(defaultUserProperties.getIntroduce());
        userAddition.setCreateAt(new Date());
        userAddition.setModifyAt(new Date());
        return userAddition;
    }
}
