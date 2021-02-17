package org.qwli.rowspot.web.controller.api;


import org.qwli.rowspot.Constants;
import org.qwli.rowspot.model.Follow;
import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.service.FollowService;
import org.qwli.rowspot.web.annotations.AuthenticatedRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 关注 api
 * @author liqiwen
 * @since 1.0
 */
@RestController
@RequestMapping("api")
public class FollowApi extends AbstractApi {

    private final FollowService followService;

    public FollowApi(FollowService followService) {
        this.followService = followService;
    }

    /**
     * check user has followed other user
     * @param userId userId
     * @param request request
     * @return ResponseEntity
     */
    @GetMapping("followed/{userId}")
    public ResponseEntity<Boolean> checkFollowed(@PathVariable("userId") Long userId, HttpServletRequest request) {

        LoggedUser loggedUser = (LoggedUser) request.getAttribute(Constants.USER);
        Boolean isFollowed = false;
        if(loggedUser != null) {
            isFollowed = followService.checkFollowed(loggedUser.getId(), userId);
        }

        return ResponseEntity.ok(isFollowed);
    }

    /**
     * 获取当前用户关注的数量
     * @param userId userId
     * @param request requestId
     * @return ResponseEntity
     */
    @GetMapping("follow/{userId}/count")
    public ResponseEntity<Map<String, Long>> countFollowData(@PathVariable("userId") Long userId, HttpServletRequest request) {
//        final LoggedUser loggedUser = getLoggedUser(request);
        return ResponseEntity.ok(followService.countFollowData(userId));
    }


    /**
     * 添加关注/取消关注
     * @param id id
     * @param request request
     * @return ResponseEntity
     */
    @AuthenticatedRequired
    @PostMapping("follow/{id}/add")
    public ResponseEntity<Void> addFollow(@PathVariable("id") Long id, HttpServletRequest request) {

        Follow follow = new Follow();
        follow.setFollowUserId(id);

        final LoggedUser loggedUser = (LoggedUser) request.getAttribute(Constants.USER);
        follow.setUserId(loggedUser.getId());

        followService.addFollow(follow);

        return ResponseEntity.ok().build();
    }
}
