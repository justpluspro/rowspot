package org.qwli.rowspot.web.controller.api;


import org.qwli.rowspot.Constants;
import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.SavedArticle;
import org.qwli.rowspot.service.ArticleService;
import org.qwli.rowspot.service.NewArticle;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Article Operator API
 * @author liqiwen
 * @since 1.2
 */
@RequestMapping("api/article")
@RestController
public class ArticleApi extends AbstractApi {

    /**
     * article Service
     */
    private final ArticleService articleService;


    public ArticleApi(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * saved article
     * @param newArticle newArticle
     * @param request request
     * @return SavedArticle
     */
    @PostMapping("saved")
    public ResponseEntity<SavedArticle> save(@RequestBody @Validated NewArticle newArticle, HttpServletRequest request) {
        LoggedUser loggedUser = (LoggedUser) request.getAttribute(Constants.USER);
        newArticle.setUserId(loggedUser.getId());
        return ResponseEntity.ok(articleService.save(newArticle));
    }





    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, HttpServletRequest request) {
        LoggedUser loggedUser = (LoggedUser) request.getAttribute(Constants.USER);
        articleService.delete(id, loggedUser.getId());
        return ResponseEntity.ok().build();
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
