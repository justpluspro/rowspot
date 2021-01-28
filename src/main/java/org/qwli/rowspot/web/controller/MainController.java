package org.qwli.rowspot.web.controller;

import org.qwli.rowspot.exception.ResourceNotFoundException;
import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.Category;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.service.ArticleService;
import org.qwli.rowspot.model.aggregate.ArticleAggregate;
import org.qwli.rowspot.service.CategoryService;
import org.qwli.rowspot.util.EnvironmentContext;
import org.qwli.rowspot.web.ArticleQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

/**
 * IndexController
 * @author liqiwen
 * @since 1.2
 */
@Controller
public class MainController {

    private final ArticleService articleService;

    private final CategoryService categoryService;


    public MainController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
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

    @GetMapping("{categoryName}/issues")
    public String categoryIssues(@PathVariable("categoryName") String categoryName,
                                 Model model) {
        final Category category = categoryService.findOne(categoryName).orElseThrow(()
                -> new ResourceNotFoundException("分类不存在"));

        ArticleQueryParam queryParam = new ArticleQueryParam();
        queryParam.setPage(1);
        queryParam.setArticleType(ArticleType.Q);

        final PageAggregate<ArticleAggregate> page = articleService.findPage(queryParam);

        model.addAttribute("page", page);
        return "front/category_issues";
    }

    /**
     * 获取文章详情
     * @param id id
     * @param model model
     * @return String
     */
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
