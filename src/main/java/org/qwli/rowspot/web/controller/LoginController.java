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

    /**
     * 去登陆页面
     * @return string
     */
    @GetMapping("login")
    public String login() {
        return "front/login";
    }

    /**
     * 注册页面
     * @return string
     */
    @GetMapping("register")
    public String register() {
        return "front/register";
    }
}
