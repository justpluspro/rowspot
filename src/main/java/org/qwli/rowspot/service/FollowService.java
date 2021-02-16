package org.qwli.rowspot.service;

import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.event.FollowedEvent;
import org.qwli.rowspot.event.UnFollowEvent;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.exception.ResourceNotFoundException;
import org.qwli.rowspot.model.Follow;
import org.qwli.rowspot.model.PropertyName;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.aggregate.FollowAggregate;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.model.enums.UserState;
import org.qwli.rowspot.repository.FollowRepository;
import org.qwli.rowspot.repository.UserRepository;
import org.qwli.rowspot.web.FollowQueryParam;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qwli7
 * @date 2021/2/8 15:34
 * 功能：FollowService
 **/
@Service
public class FollowService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public PageAggregate<FollowAggregate> findPage(FollowQueryParam queryParam) throws ResourceNotFoundException {
        User user = userRepository.findById(queryParam.getUserId()).orElseThrow(()
                -> new ResourceNotFoundException(MessageEnum.USER_NOT_EXISTS));

        PageRequest pageRequest = PageRequest.of(queryParam.getPage()-1, queryParam.getSize(),
                Sort.by(Sort.Order.desc(PropertyName.createAt)));

        Follow probe = new Follow();

        Boolean queryType = queryParam.getQueryType();
        if(queryType) {
            probe.setFollowUserId(user.getId());
        } else {
            probe.setUserId(user.getId());
        }
        Example<Follow> example = Example.of(probe);

        Page<Follow> followPage = followRepository.findAll(example, pageRequest);
        List<Follow> contents = followPage.getContent();
        if(contents.isEmpty()) {
            return new PageAggregate<>(Collections.emptyList(), queryParam.getPage(), 0);
        }
        List<FollowAggregate> followAggregates = followPage.stream().map(e -> {
            FollowAggregate followAggregate = new FollowAggregate();

            userRepository.findById(e.getFollowUserId()).ifPresent(e1 -> {
                followAggregate.setFollowUserId(e1.getId());
                followAggregate.setFollowUserAvatar(e1.getAvatar());
                followAggregate.setFollowUserPrestige(0L);
                followAggregate.setFollowUsername(e1.getUsername());
            });
            followAggregate.setCreateAt(e.getCreateAt());
            return followAggregate;
        }).collect(Collectors.toList());

        return new PageAggregate<>(followAggregates, queryParam.getPage(), followPage.getTotalPages());
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void addUnFollow(Follow follow) throws BizException {


    }


    @Transactional(readOnly = true)
    public Map<String, Long> countFollowData(Long userId) throws BizException {
        userRepository.findById(userId).orElseThrow(() -> new BizException(MessageEnum.USER_NOT_EXISTS));

        // following count
        Follow probe = new Follow();
        probe.setUserId(userId);
        Example<Follow> followingExample = Example.of(probe);
        long followingCount = followRepository.count(followingExample);

        // followedCount
        probe.setFollowUserId(userId);
        probe.setUserId(null);
        Example<Follow> followedExample = Example.of(probe);
        long followedCount = followRepository.count(followedExample);
        Map<String, Long> countFollow = new HashMap<>(2);
        countFollow.put("followingCount", followingCount);
        countFollow.put("followedCount", followedCount);
        return countFollow;

    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void addFollow(Follow follow) throws BizException {

        Long userId = follow.getUserId();
        Long followUserId = follow.getFollowUserId();

        //check is self
        if(userId.equals(followUserId)) {
            throw new BizException(MessageEnum.FOLLOW_ERROR);
        }

        //check user
        User user = userRepository.findById(userId).orElseThrow(()
                -> new BizException(MessageEnum.USER_NOT_EXISTS));
        if(user.getState() != UserState.NORMAL) {
            throw new BizException(MessageEnum.USER_STATE_INVALID);
        }

        //check follow user
        User followedUser = userRepository.findById(followUserId).orElseThrow(()
                -> new BizException(MessageEnum.USER_NOT_EXISTS));
        if(followedUser.getState() != UserState.NORMAL) {
            throw new BizException(MessageEnum.USER_STATE_INVALID);
        }

        //check has followed?
        Optional<Follow> followOptional = followRepository.findByUserIdAndFollowUserId(userId, followUserId);
        if(!followOptional.isPresent()) {
            follow.setCreateAt(new Date());
            follow.setModifyAt(new Date());
            followRepository.save(follow);

            //publish follow event
            applicationEventPublisher.publishEvent(new FollowedEvent(this, user, followedUser));

        } else {
            //cancel follow
            followRepository.delete(followOptional.get());
            //publish unfollow event
            applicationEventPublisher.publishEvent(new UnFollowEvent(this));
        }
    }

    /**
     * 检查用户是否关注了某个用户
     * @param followUserId followUserId
     * @param userId userId
     * @return Boolean
     * @throws BizException BizException
     */
    @Transactional(readOnly = true)
    public Boolean checkFollowed(Long followUserId, Long userId) throws BizException {
        userRepository.findById(followUserId).orElseThrow(() -> new BizException(MessageEnum.USER_NOT_EXISTS));
        userRepository.findById(userId).orElseThrow(() -> new BizException(MessageEnum.USER_NOT_EXISTS));
        return followRepository.findByUserIdAndFollowUserId(userId, followUserId).isPresent();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

}