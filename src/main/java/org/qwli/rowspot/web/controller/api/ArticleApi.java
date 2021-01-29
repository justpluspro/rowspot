package org.qwli.rowspot.web.controller.api;


import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.SavedArticle;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.service.ArticleService;
import org.qwli.rowspot.service.NewArticle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 文章操作 API
 * @author liqiwen
 * @since 1.2
 */
@RequestMapping("api/article")
@RestController
public class ArticleApi extends AbstractApi {

    private final ArticleService articleService;

    public ArticleApi(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * 发布文章
     * @param newArticle newArticle
     * @param request request
     * @return SavedArticle
     */
    @PostMapping("saved")
    public ResponseEntity<SavedArticle> save(@RequestBody NewArticle newArticle, HttpServletRequest request) {
        LoggedUser loggedUser = (LoggedUser) request.getAttribute("user");
        newArticle.setUserId(loggedUser.getId());
        return ResponseEntity.ok(articleService.save(newArticle));
    }

    /**
     * 点击量
     * @param id id
     * @return ResponseEntity
     */
    @PatchMapping("{id}/hits")
    public ResponseEntity<Void> hit(@PathVariable("id") Long id) {
        articleService.hit(id);
        return ResponseEntity.ok().build();
    }
}
