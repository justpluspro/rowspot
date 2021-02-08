package org.qwli.rowspot.web.controller.api;


import org.qwli.rowspot.Constants;
import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api")
public class FollowApi extends AbstractApi {

    private final FollowService followService;

    public FollowApi(FollowService followService) {
        this.followService = followService;
    }

    /**
     * 检查某个用户是否关注了某个用户
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
}
