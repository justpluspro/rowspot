package org.qwli.rowspot.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 登录控制器
 * @author liqiwen
 * @since 1.2
 */
@Controller
public class LoginController {

    @GetMapping("login")
    public String login() {
        return "front/login";
    }
}
