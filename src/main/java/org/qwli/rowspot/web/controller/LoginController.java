package org.qwli.rowspot.web.controller;


import org.qwli.rowspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {


    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login() {
        return "front/login";
    }


    @PostMapping("session")
    @ResponseBody
    public ResponseEntity<Void> session(String email, String password) {

//        userService.auth;


        return ResponseEntity.noContent().build();

    }
}
