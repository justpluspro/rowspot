package org.qwli.rowspot.service;

import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.config.DefaultUserProperties;
import org.qwli.rowspot.event.UserLoginEvent;
import org.qwli.rowspot.event.UserRegisterEvent;
import org.qwli.rowspot.exception.LoginFailException;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    private final DefaultMailProcessor defaultMailProcessor;

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
        final Optional<User> userOptional = userRepository.findUserByEmail(registerEmail);

        if(userOptional.isPresent()) {
            //用户存在
            final User user = userOptional.get();
            final UserState state = user.getState();
            if(UserState.LOCKED.equals(state)) {
                //被锁定的账号
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
                throw new BizException(MessageEnum.EMAIL_REGISTERED);
            }
        }


        //不存在，创建新的用户，新的 Addition
        final User user = UserFactory.createUser(newUser, defaultUserProperties);
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, user));
    }

    @Transactional(readOnly = true)
    public UserAggregateRoot getUserProfile(long id) throws ResourceNotFoundException {
        final User user = userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(MessageEnum.USER_NOT_EXISTS));

        final UserAddition userAddition = userAdditionRepository.findUserAdditionByUserId(id)
                .orElse(new UserAddition());

        return new UserAggregateRoot(user, userAddition);
    }

    /**
     * 用户认证
     * @param user user
     * @return User
     * @throws LoginFailException loginFailException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LoginFailException.class)
    public LoggedUser login(User user) throws LoginFailException {
        User probe = new User();
        probe.setEmail(user.getEmail());
        Example<User> example = Example.of(probe);

        final User existsUser = userRepository.findOne(example).orElseThrow(()
                -> new LoginFailException(MessageEnum.AUTH_FAILED));

        final String password = existsUser.getPassword();
        final String comparePwd = user.getPassword();

        final UserState state = existsUser.getState();
        if(state == UserState.LOCKED) {
            throw new LoginFailException(MessageEnum.AUTH_FAILED);
        }
        if(state == UserState.UNACTIVATED) {
            throw new LoginFailException(MessageEnum.AUTH_FAILED);
        }

        if(!Md5Util.md5(comparePwd).equals(password)) {
            throw new LoginFailException(MessageEnum.AUTH_FAILED);
        }
        user.setId(existsUser.getId());
        applicationEventPublisher.publishEvent(new UserLoginEvent(this, user));
        return new LoggedUser(existsUser.getId(), existsUser.getUsername(), existsUser.getEmail());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void updateUser(User user) throws BizException {
        User dbUser = checkUser(user.getId());
        dbUser.setUsername(user.getUsername());
        dbUser.setModifyAt(new Date());
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void updateUserAddition(UserAddition userAddition) throws BizException {
        User user = checkUser(userAddition.getUserId());

        final Optional<UserAddition> userAdditionOptional = userAdditionRepository.findUserAdditionByUserId(user.getId());
        if(userAdditionOptional.isPresent()) {

            final UserAddition dbUserAddition = userAdditionOptional.get();
            dbUserAddition.setJob(userAddition.getJob());
            dbUserAddition.setSkills(userAddition.getSkills());
            dbUserAddition.setAddress(userAddition.getAddress());
            dbUserAddition.setWebsite(userAddition.getWebsite());
            dbUserAddition.setIntroduce(userAddition.getIntroduce());
            dbUserAddition.setModifyAt(new Date());
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void updateUserPassword(User user) throws BizException {
        User dbUser = checkUser(user.getId());
        final String password = user.getPassword();
        if(Md5Util.md5(password).equals(dbUser.getPassword())) {
            return;
        }
        dbUser.setPassword(Md5Util.md5(user.getPassword()));
        dbUser.setModifyAt(new Date());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void updateUserAvatar(User user) throws BizException {
        User dbUser = checkUser(user.getId());
        dbUser.setAvatar(user.getAvatar());
        dbUser.setModifyAt(new Date());
    }



    private User checkUser(Long id) throws BizException {
        //check user exists
        final User dbUser = userRepository.findById(id).orElseThrow(()
                -> new BizException(MessageEnum.USER_NOT_EXISTS));
        final UserState dbUserState = dbUser.getState();
        if(dbUserState != UserState.NORMAL) {
            throw new BizException(MessageEnum.USER_STATE_INVALID);
        }
        return dbUser;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }



}
