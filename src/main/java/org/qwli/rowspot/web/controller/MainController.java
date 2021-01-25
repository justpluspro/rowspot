package org.qwli.rowspot.web.controller;

import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.service.ArticleService;
import org.qwli.rowspot.model.aggregate.ArticleAggregate;
import org.qwli.rowspot.util.EnvironmentContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    private final ArticleService articleService;


    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Article> latestNews = articleService.findLatestNews(ArticleType.A);
        model.addAttribute("latestNews", latestNews);

        final List<Article> latestArticles = articleService.findLatestNews(ArticleType.Q);
        model.addAttribute("latestArticles", latestArticles);

        return "front/index2";
    }


    @GetMapping("{categoryName}/index")
    public String categoryIndex(@PathVariable("categoryName") String categoryName) {
        ArticleAggregate articleAggregate = articleService.findIndexUnique(categoryName);

        if(articleAggregate != null) {

            return "front/a_detail";
        }


        return "front/a_detail";
    }

    @GetMapping("{id}")
    public String detail(@PathVariable("id") String id, Model model) {

        ArticleAggregate articleAggregate = articleService.findById(id);

        model.addAttribute(articleAggregate);

        return "front/detail";

    }

    @RequestMapping("isAuthenticated")
    @ResponseBody
    public ResponseEntity<Boolean> isAuthenticated(){
        return ResponseEntity.ok(EnvironmentContext.isAuthenticated());
    }
}
