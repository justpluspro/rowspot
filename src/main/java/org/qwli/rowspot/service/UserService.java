package org.qwli.rowspot.service;

import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.event.UserLoginEvent;
import org.qwli.rowspot.exception.ResourceNotFoundException;
import org.qwli.rowspot.model.aggregate.UserAggregateRoot;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserAddition;
import org.qwli.rowspot.model.factory.UserFactory;
import org.qwli.rowspot.repository.UserAdditionRepository;
import org.qwli.rowspot.repository.UserRepository;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.service.processor.MailHandler;
import org.qwli.rowspot.util.Md5Util;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户业务实现类
 * @author liqiwen
 */
@Service
public class UserService extends AbstractService<User, User> {

    private ApplicationEventPublisher applicationEventPublisher;

    private final UserRepository userRepository;

    @Resource
    private MailHandler mailHandler;

    private final UserAdditionRepository userAdditionRepository;

    public UserService(UserRepository jpaUserRepository,
                       UserAdditionRepository userAdditionMapper) {
        this.userRepository = jpaUserRepository;
        this.userAdditionRepository = userAdditionMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void register(NewUser newUser) throws BizException {
        final String registerEmail = newUser.getRegisterEmail();
        //存在. 重新发一份注册邮件
        final Optional<User> dbUserOptional = userRepository.findByEmail(registerEmail);
        if(!dbUserOptional.isPresent()) {
            //不存在，创建新的用户，新的 Addition
            final User user = UserFactory.createUser(newUser);
            userRepository.save(user);
            UserAddition userAddition = UserFactory.createUserAddition(user);
            userAdditionRepository.save(userAddition);
        }
    }

    @Transactional(readOnly = true)
    public UserAggregateRoot getUserProfile(String id) throws ResourceNotFoundException {
        if(StringUtils.isEmpty(id)) {
            throw new ResourceNotFoundException("invalid user id");
        }
        final User user = userRepository.findById(Long.parseLong(id)).orElseThrow(()
                -> new ResourceNotFoundException("user not exists"));

        final UserAddition userAddition = userAdditionRepository.findUserAdditionByUserId(Long.parseLong(id))
                .orElse(new UserAddition());

        return new UserAggregateRoot(user, userAddition);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public User login(User user) throws BizException {
        final User db = userRepository.findByEmail(user.getEmail()).orElseThrow(()
                -> new BizException(MessageEnum.USER_NOT_EXISTS));
        final String password = db.getPassword();
        final String comparePwd = user.getPassword();

        if(!Md5Util.md5(comparePwd).equals(password)) {
            throw new BizException(MessageEnum.USER_UNACTIVATED);
        }
        user.setId(db.getId());
        applicationEventPublisher.publishEvent(new UserLoginEvent(this, user));

//        try {
//            mailHandler.sendHtmlMail();
//            mailHandler.sendSimpleMail();
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }

        return db;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
