package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.model.User;
import org.qwli.rowspot.service.UserService;
import org.qwli.rowspot.util.EnvironmentContext;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("api")
@RestController
public class LoginApi {


    private final UserService userService;

    public LoginApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("token")
    public ResponseEntity<User> session(@RequestBody User user,
                                           HttpServletRequest request) {

        if(StringUtils.isEmpty(user.getEmail()) || StringUtils.isEmpty(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        user = userService.login(user);

        request.getSession().setAttribute("user", user);

        return ResponseEntity.ok(user);
    }

    @PostMapping("exit")
    public ResponseEntity<Boolean> exit(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        session.removeAttribute("user");
        request.removeAttribute("user");
        EnvironmentContext.removeAll();
        return ResponseEntity.ok(true);
    }
}
