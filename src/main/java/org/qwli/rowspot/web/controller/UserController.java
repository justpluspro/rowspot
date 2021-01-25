package org.qwli.rowspot.web.controller;


import org.qwli.rowspot.service.ArticleService;
import org.qwli.rowspot.service.CollectService;
import org.qwli.rowspot.service.UserService;
import org.qwli.rowspot.model.aggregate.ArticleAggregate;
import org.qwli.rowspot.model.aggregate.CollectAggregate;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.model.aggregate.UserAggregateRoot;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.web.ArticleQueryParam;
import org.qwli.rowspot.web.annotations.AuthenticatedRequired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("user")
@Controller
public class UserController {

    private final UserService userService;

    private final ArticleService articleService;

    private final CollectService collectService;

    public UserController(UserService userService, ArticleService articleService,
                          CollectService collectService) {
        this.userService = userService;
        this.articleService = articleService;
        this.collectService = collectService;
    }

    /**
     * 首页
     * @param id id
     * @param model model
     * @return String
     */
    @GetMapping("{id}/profile")
    public String userProfile(@PathVariable("id") String id, Model model) {
        UserAggregateRoot userAggregateRoot = userService.getUserProfile(id);
        model.addAttribute("userAggregateRoot", userAggregateRoot);

        return "front/user/profiles";
    }


    /**
     * 用户文章
     * @param model model
     * @return String
     */
    @GetMapping("{id}/articles")
    public String articles(@PathVariable("id") String userId,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           Model model) {

        ArticleQueryParam queryParam = new ArticleQueryParam();

        queryParam.setUserId(userId);
        queryParam.setPage(page);

        queryParam.setArticleType(ArticleType.A);

        final PageAggregate<ArticleAggregate> pageAggregate = articleService.findPage(queryParam);
        model.addAttribute(pageAggregate);

        final UserAggregateRoot userAggregateRoot = userService.getUserProfile(userId);
        model.addAttribute(userAggregateRoot);

        return "front/user/articles";
    }


    /**
     * 用户问答
     * @param model model
     * @return string
     */
    @GetMapping("{id}/questions")
    public String questions(@PathVariable("id") String userId,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            Model model) {
        final UserAggregateRoot userAggregateRoot = userService.getUserProfile(userId);
        model.addAttribute(userAggregateRoot);

        ArticleQueryParam queryParam = new ArticleQueryParam();
        queryParam.setArticleType(ArticleType.Q);

        queryParam.setUserId(userId);
        queryParam.setPage(page);
        final PageAggregate<ArticleAggregate> pageAggregate = articleService.findPage(queryParam);
        model.addAttribute(pageAggregate);

        return "front/user/questions";
    }

    /**
     * 用户回答
     * @param id id
     * @param model model
     * @return String
     */
    @GetMapping("{id}/answers")
    public String answers(@PathVariable("id") String id, Model model) {
        final UserAggregateRoot userAggregateRoot = userService.getUserProfile(id);
        model.addAttribute(userAggregateRoot);
        return "front/user/answers";
    }

    /**
     * 用户收藏
     * @param id id
     * @param model model
     * @return String
     */
    @GetMapping("{id}/collects")
    public String collect(@PathVariable("id") String id,
                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                          Model model) {
        final UserAggregateRoot userAggregateRoot = userService.getUserProfile(id);
        model.addAttribute(userAggregateRoot);

        final PageAggregate<CollectAggregate> aggregatePageData = collectService.findPage(page, id);

        model.addAttribute(aggregatePageData);


        return "front/user/collects";
    }


    /**
     * 用户关注
     * @param id id
     * @return String
     */
    @AuthenticatedRequired
    @GetMapping("{id}/following")
    public String following(@PathVariable("id") String id) {

        return "front/user/following";
    }


    /**
     * 用户粉丝
     * @param id id
     * @return String
     */
    @AuthenticatedRequired
    @GetMapping("{id}/followed")
    public String followed(@PathVariable("id") String id) {

        return "front/user/following";
    }


    @GetMapping("share")
    @AuthenticatedRequired
    public String create(@RequestParam("type") String type) {
        return "front/write";
    }

    @GetMapping("question")
    @AuthenticatedRequired
    public String question() {
        return "front/ask3";
    }

}
