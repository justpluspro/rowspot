package org.qwli.rowspot.web.controller;

import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.Category;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.model.aggregate.TypeAggregate;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.service.ArticleService;
import org.qwli.rowspot.model.aggregate.ArticleAggregate;
import org.qwli.rowspot.service.CategoryService;
import org.qwli.rowspot.util.EnvironmentContext;
import org.qwli.rowspot.web.ArticleQueryParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IndexController
 * @author liqiwen
 * @since 1.2
 */
@Controller
public class MainController extends AbstractController {

    private final ArticleService articleService;

    public MainController(ArticleService articleService, CategoryService categoryService) {
        super(categoryService);
        this.articleService = articleService;
    }

    /**
     * 首页
     * @param model model
     * @return String
     */
    @GetMapping("/")
    public String index(Model model) {
        List<Article> latestNews = articleService.findLatestNews(ArticleType.A);
        model.addAttribute("latestNews", latestNews);

        final List<Article> latestArticles = articleService.findLatestNews(ArticleType.Q);
        model.addAttribute("latestArticles", latestArticles);

        return "front/index2";
    }

    /**
     * 分类首页
     * @param alias alias
     * @param model Model
     * @return String
     */
    @GetMapping(value = {"{alias}/index"})
    public String categoryIndex(@PathVariable("alias") String alias, Model model) {
        ArticleAggregate articleAggregate = articleService.findIndexUnique(alias);
        model.addAttribute("articleAggregate", articleAggregate);
        return "front/detail";
    }

    /**
     * 问题 issues
     * @param alias alias
     * @param page page
     * @param model model
     * @return String
     */
    @GetMapping("{alias}/issues")
    public String categoryIssues(@PathVariable("alias") String alias,
                                 @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                 Model model) {
        Category category = validCategory(alias);
        ArticleQueryParam queryParam = new ArticleQueryParam();
        queryParam.setPage(page);
        queryParam.setArticleType(ArticleType.Q);
        queryParam.setCategoryId(category.getId());

        final List<TypeAggregate> typeAggregates = ArticleType.findAll(category, ArticleType.Q);
        model.addAttribute("typeAggregates", typeAggregates);


        final PageAggregate<ArticleAggregate> pageData = articleService.findPage(queryParam);

        model.addAttribute("page", pageData);
        return "front/category_issues";
    }


    /**
     * 分类动态
     * @param alias alias
     * @param page page
     * @param model model
     * @return String
     */
    @GetMapping("{alias}/news")
    public String categoryNews(@PathVariable("alias") String alias,
                               @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                               Model model) {
        final Category category = validCategory(alias);

        ArticleQueryParam queryParam = new ArticleQueryParam();
        queryParam.setPage(page);
        queryParam.setArticleType(ArticleType.N);
        queryParam.setCategoryId(category.getId());

        final List<TypeAggregate> typeAggregates = ArticleType.findAll(category, ArticleType.N);
        model.addAttribute("typeAggregates", typeAggregates);

        final PageAggregate<ArticleAggregate> pageData = articleService.findPage(queryParam);

        model.addAttribute("page", pageData);
        return "front/category_news";
    }

    /**
     * 工具
     * @param alias alias
     * @param page page
     * @param model model
     * @return String
     */
    @GetMapping("{alias}/tools")
    public String categoryTools(@PathVariable("alias") String alias,
                               @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                               Model model) {
        final Category category = validCategory(alias);
        ArticleQueryParam queryParam = new ArticleQueryParam();
        queryParam.setPage(page);
        queryParam.setArticleType(ArticleType.T);
        queryParam.setCategoryId(category.getId());

        final List<TypeAggregate> typeAggregates = ArticleType.findAll(category, ArticleType.T);
        model.addAttribute("typeAggregates", typeAggregates);

        final PageAggregate<ArticleAggregate> pageData = articleService.findPage(queryParam);

        model.addAttribute("page", pageData);
        return "front/category_news";
    }

    /**
     * 获取文章详情
     * @param id id
     * @param model model
     * @return String
     */
    @GetMapping("{id}")
    public String detail(@PathVariable("id") Long id, Model model) {

        ArticleAggregate articleAggregate = articleService.findById(id);

        model.addAttribute(articleAggregate);

        return "front/detail";

    }

    /**
     * 用户是否登录了
     * @return boolean
     */
    @RequestMapping("isAuthenticated")
    @ResponseBody
    public ResponseEntity<Boolean> isAuthenticated(){
        return ResponseEntity.ok(EnvironmentContext.isAuthenticated());
    }
}
