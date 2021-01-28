package org.qwli.rowspot.event;

import org.qwli.rowspot.config.DefaultUserProperties;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserAddition;
import org.qwli.rowspot.model.factory.UserFactory;
import org.qwli.rowspot.repository.UserAdditionRepository;
import org.qwli.rowspot.repository.UserRepository;
import org.qwli.rowspot.service.processor.EmailBean;
import org.qwli.rowspot.service.processor.MailProcessor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
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

    private final MailProcessor mailProcessor;

    public UserEventListener(UserRepository userRepository,UserAdditionRepository userAdditionRepository,
                             DefaultUserProperties defaultUserProperties,
                             MailProcessor mailProcessor) {
        this.userRepository = userRepository;
        this.defaultUserProperties = defaultUserProperties;
        this.userAdditionRepository = userAdditionRepository;
        this.mailProcessor = mailProcessor;
    }

    @EventListener(classes = UserLoginEvent.class)
    public void processUserLoginEvent(UserLoginEvent event) {
        Assert.notNull(event, "UserLoginEvent not null.");
        final User user = event.getUser();
        userRepository.updateUserLoginDate(user.getId(), new Date());
    }

    /**
     * 用户注册事件
     * 1、添加附属额外信息
     * 2、发送注册激活邮件
     * @param event event
     */
    @EventListener(classes = UserRegisterEvent.class)
    public void processUserRegisterEvent(UserRegisterEvent event) {
        Assert.notNull(event, "UserRegisterEvent not null.");
        final User user = event.getUser();
        UserAddition userAddition = UserFactory.createUserAddition(user, defaultUserProperties);
        userAdditionRepository.save(userAddition);
        //发送激活邮箱
        EmailBean emailBean = new EmailBean();

        String mailContent = "<h1>欢迎注册</h1>" +
                "<p>感谢注册xxx服务，请请点击<a href=''>这里</a>确认激活</p>";
        emailBean.setContent(mailContent);
        emailBean.setSubject("Rowspot 激活");
        emailBean.setHtml(true);
        emailBean.setSendTos(Collections.singletonList(user.getEmail()));
        emailBean.setFrom("");
        mailProcessor.sendHtmlMail(emailBean);
    }
}
