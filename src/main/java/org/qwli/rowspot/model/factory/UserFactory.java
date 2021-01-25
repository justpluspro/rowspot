package org.qwli.rowspot.model.factory;

import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserAddition;
import org.qwli.rowspot.model.enums.UserState;
import org.qwli.rowspot.service.NewUser;

import java.util.Date;

public class UserFactory {



    public static User createUser(NewUser newUser) {
        User user = new User();
        user.setUsername(newUser.getRegisterName());
        user.setEmail(newUser.getRegisterEmail());

        user.setCreateAt(new Date());
        user.setModifyAt(new Date());
        user.setAvatar("/static/img/default-user.png");
        user.setState(UserState.UNACTIVATED);

        return user;
    }

    public static UserAddition createUserAddition(User user) {
        UserAddition userAddition = new UserAddition();
        userAddition.setUserId(user.getId());
        userAddition.setAddress("北京-天安门");
        userAddition.setJob("开发工程师");
        userAddition.setSkills("Java");
        userAddition.setWebsite("http://www.baidu.com");
        userAddition.setIntroduce("这人真懒，什么都没有留下");
        userAddition.setCreateAt(new Date());
        userAddition.setModifyAt(new Date());
        return userAddition;
    }
}
