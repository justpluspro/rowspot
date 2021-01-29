package org.qwli.rowspot.service;

import org.qwli.rowspot.Message;
import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.config.DefaultUserProperties;
import org.qwli.rowspot.event.UserLoginEvent;
import org.qwli.rowspot.event.UserRegisterEvent;
import org.qwli.rowspot.exception.ResourceNotFoundException;
import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.aggregate.UserAggregateRoot;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserAddition;
import org.qwli.rowspot.model.enums.UserState;
import org.qwli.rowspot.model.factory.UserFactory;
import org.qwli.rowspot.repository.UserAdditionRepository;
import org.qwli.rowspot.repository.UserRepository;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.service.processor.DefaultMailProcessor;
import org.qwli.rowspot.service.processor.EmailBean;
import org.qwli.rowspot.util.DateUtil;
import org.qwli.rowspot.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

/**
 * 用户业务实现类
 * @author liqiwen
 */
@Service
public class UserService extends AbstractService<User, User> {

    private ApplicationEventPublisher applicationEventPublisher;

    private final UserRepository userRepository;

    private final DefaultUserProperties defaultUserProperties;

    private DefaultMailProcessor defaultMailProcessor;

    private final UserAdditionRepository userAdditionRepository;

    public UserService(UserRepository jpaUserRepository,
                       UserAdditionRepository userAdditionMapper,
                       DefaultMailProcessor defaultMailProcessor,
                       DefaultUserProperties defaultUserProperties) {
        this.userRepository = jpaUserRepository;
        this.userAdditionRepository = userAdditionMapper;
        this.defaultUserProperties = defaultUserProperties;
        this.defaultMailProcessor = defaultMailProcessor;
    }

    /**
     * 用户注册
     * @param newUser newUser
     * @throws BizException BizException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void register(NewUser newUser) throws BizException {
        final String registerEmail = newUser.getRegisterEmail();
        //存在. 重新发一份注册邮件
        final Optional<User> userOptional = userRepository.findByEmail(registerEmail);

        if(!userOptional.isPresent()) {
            //不存在，创建新的用户，新的 Addition
            final User user = UserFactory.createUser(newUser, defaultUserProperties);
            userRepository.save(user);
            applicationEventPublisher.publishEvent(new UserRegisterEvent(this, user));
        } else {
            //用户存在
            final User user = userOptional.get();
            final UserState state = user.getState();
            if(UserState.LOCKED.equals(state)) {
                throw new BizException(MessageEnum.USER_LOCKED);
            }
            //未激活，判断发送激活邮件时间
            if(UserState.UNACTIVATED.equals(state)) {

                final Date modifyAt = user.getModifyAt();
                //获取当前时间和修改时间的间隔，如果超过了 24 小时，重新发邮件，否则异常

                if(DateUtil.getHoursFromNow(modifyAt) > 24) {
                    EmailBean registerEmailBean = new EmailBean();
                    defaultMailProcessor.sendHtmlMail(registerEmailBean);
                    return;
                }

                logger.error("user unactivated! enter into your email for activating!");
                throw new BizException(MessageEnum.USER_UNACTIVATED);
            }

            if(UserState.NORMAL.equals(state)) {
                //账号正常，请去登录
                throw new BizException(MessageEnum.USER_LOCKED);
            }

        }
    }

    @Transactional(readOnly = true)
    public UserAggregateRoot getUserProfile(long id) throws ResourceNotFoundException {
        final User user = userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("user not exists"));

        final UserAddition userAddition = userAdditionRepository.findUserAdditionByUserId(id)
                .orElse(new UserAddition());

        return new UserAggregateRoot(user, userAddition);
    }

    /**
     * 用户认证
     * @param user user
     * @return User
     * @throws BizException BizException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public LoggedUser login(User user) throws BizException {
        final User db = userRepository.findByEmail(user.getEmail()).orElseThrow(()
                -> new BizException(MessageEnum.USER_NOT_EXISTS));
        final String password = db.getPassword();
        final String comparePwd = user.getPassword();

        final UserState state = db.getState();
        if(state == UserState.LOCKED) {
            throw new BizException(MessageEnum.USER_LOCKED);
        }
        if(state == UserState.UNACTIVATED) {
            throw new BizException(MessageEnum.USER_UNACTIVATED);
        }

        if(!Md5Util.md5(comparePwd).equals(password)) {
            throw new BizException(MessageEnum.AUTH_FAILED);
        }
        user.setId(db.getId());
        applicationEventPublisher.publishEvent(new UserLoginEvent(this, user));


        LoggedUser loggedUser = new LoggedUser();
        loggedUser.setId(db.getId());
        loggedUser.setUsername(db.getUsername());
        loggedUser.setEmail(db.getEmail());

        return loggedUser;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
