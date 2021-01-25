package org.qwli.rowspot.web.controller.api;


import org.qwli.rowspot.model.User;
import org.qwli.rowspot.service.ArticleService;
import org.qwli.rowspot.service.NewArticle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("api/article")
@RestController
public class ArticleApi {


    private final ArticleService articleService;

    public ArticleApi(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("saved")
    public ResponseEntity<Void> save(@RequestBody NewArticle newArticle, HttpServletRequest request) {

        User user = (User) request.getAttribute("user");
        newArticle.setUserId(user.getId());
        articleService.save(newArticle);

        return ResponseEntity.noContent().build();
    }
}
