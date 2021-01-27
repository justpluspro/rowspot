package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.model.Comment;
import org.qwli.rowspot.model.LoggedUser;
import org.qwli.rowspot.model.SavedComment;
import org.qwli.rowspot.model.aggregate.CommentAggregate;
import org.qwli.rowspot.service.CommentService;
import org.qwli.rowspot.service.NewComment;
import org.qwli.rowspot.web.CommentQueryParam;
import org.qwli.rowspot.web.annotations.AuthenticatedRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 评论 API
 * @author liqiwen
 * @since 1.2
 */
@RestController
@RequestMapping("api")
public class CommentApi extends AbstractApi {


    private final CommentService commentService;

    public CommentApi(CommentService commentService) {
        this.commentService = commentService;
    }


    /**
     * 保存评论
     * @param newComment comment
     * @param request request
     * @return ResponseEntity
     */
    @PostMapping("comment/saved")
    @AuthenticatedRequired
    public ResponseEntity<SavedComment> save(@RequestBody NewComment newComment, HttpServletRequest request) {

        LoggedUser loggedUser = (LoggedUser) request.getAttribute("user");

        newComment.setUserId(loggedUser.getId());

        final SavedComment savedComment = commentService.save(newComment);

        return ResponseEntity.ok(savedComment);

    }

    /**
     * 分页查询评论
     * @param articleId articleId
     * @param page page
     * @return ResponseEntity
     */
    @GetMapping("article/{id}/comments")
    @ResponseBody
    public ResponseEntity<List<CommentAggregate>> findPage(@PathVariable("id") Long articleId,
                                                  @RequestParam("page") Integer page) {

        CommentQueryParam commentQueryParam = new CommentQueryParam();
        commentQueryParam.setPage(page);
        commentQueryParam.setArticleId(articleId);

        List<CommentAggregate> datas = commentService.findPage(commentQueryParam);

        return ResponseEntity.ok(datas);


    }

}
