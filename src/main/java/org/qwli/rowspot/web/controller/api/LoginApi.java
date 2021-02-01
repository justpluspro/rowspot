package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.service.NewUser;
import org.qwli.rowspot.service.UserService;
import org.qwli.rowspot.util.EnvironmentContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录认证 API
 * @author liqiwen
 * @since 1.0
 */
@RequestMapping("api")
@RestController
public class LoginApi extends AbstractApi {

    private final UserService userService;

    public LoginApi(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录
     * @param loginUser user
     * @param request request
     * @return LoggedUser
     */
    @PostMapping("token")
    public ResponseEntity<LoggedUser> session(@RequestBody @Validated LoginUser loginUser,
                                              HttpServletRequest request) {

        User user = new User();
        user.setEmail(loginUser.getMail());
        user.setPassword(loginUser.getPassword());

        LoggedUser loggedUser = userService.login(user);

        logger.info("loggedUser: [{}]", loggedUser);
        request.getSession().setAttribute("user", loggedUser);

        return ResponseEntity.ok(loggedUser);
    }


    /**
     * 用户注册
     * @param newUser newUser
     * @return ResponseEntity
     */
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody @Validated NewUser newUser) {
        userService.register(newUser);
        return ResponseEntity.ok("注册成功, 请前往邮箱确认");
    }

    /**
     * 退出登录
     * @param request request
     * @return Boolean
     */
    @PostMapping("exit")
    public ResponseEntity<Boolean> exit(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.removeAttribute("user");
        request.removeAttribute("user");
        EnvironmentContext.removeAll();
        return ResponseEntity.ok(true);
    }
}
