package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.UserAddition;
import org.qwli.rowspot.model.enums.UserPasswordChanged;
import org.qwli.rowspot.service.PasswordChanged;
import org.qwli.rowspot.service.UserService;
import org.qwli.rowspot.web.annotations.AuthenticatedRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 * @author liqiwen
 */
@RequestMapping("api")
@RestController
public class UserApi extends AbstractApi {


    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    /**
     * update user
     * @param user user
     * @param request request
     * @return ResponseEntity
     */
    @PutMapping("user/update")
    @AuthenticatedRequired
    public ResponseEntity<Void> updateUser(@RequestBody User user, HttpServletRequest request) {
        LoggedUser loggedUser = (LoggedUser) request.getAttribute("user");
        user.setId(loggedUser.getId());

        userService.updateUser(user);

        return ResponseEntity.ok().build();
    }

    /**
     * update user addition
     * @param userAddition userAddition
     * @param request request
     * @return ResponseEntity
     */
    @PutMapping("user/addition/update")
    @AuthenticatedRequired
    public ResponseEntity<Void> updateUserAddition(@RequestBody UserAddition userAddition, HttpServletRequest request) {
        LoggedUser loggedUser = (LoggedUser) request.getAttribute("user");
        userAddition.setUserId(loggedUser.getId());
        userService.updateUserAddition(userAddition);
        return ResponseEntity.ok().build();
    }

    /**
     * update user password
     * @param passwordChanged passwordChanged
     * @param request request
     * @return ResponseEntity
     */
    @PutMapping("user/password/update")
    @AuthenticatedRequired
    public ResponseEntity<Void> updateUserPassword(@RequestBody PasswordChanged passwordChanged, HttpServletRequest request) {

        LoggedUser loggedUser = (LoggedUser) request.getAttribute("user");
        passwordChanged.setId(loggedUser.getId());

        userService.updateUserPassword(passwordChanged);
        return ResponseEntity.ok().build();
    }

    /**
     * update user avatar
     * @param user user
     * @param request request
     * @return ResponseEntity
     */
    @PutMapping("user/avatar/update")
    @AuthenticatedRequired
    public ResponseEntity<Void> updateUserAvatar(@RequestBody User user, HttpServletRequest request) {

        LoggedUser loggedUser = (LoggedUser) request.getAttribute("user");
        user.setId(loggedUser.getId());

        userService.updateUserAvatar(user);
        return ResponseEntity.ok().build();
    }
}
