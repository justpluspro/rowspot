package org.qwli.rowspot.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {



    @RequestMapping("create")
    public String create() {

        return "front/write";
    }



}
