package org.qwli.rowspot.model.factory;

import org.qwli.rowspot.model.Comment;
import org.qwli.rowspot.service.NewComment;

import java.util.Date;

/**
 * @author qwli7
 * @date 2021/1/27 13:36
 * 功能：CommentFactory
 **/
public class CommentFactory {

    public static Comment createNewComment(NewComment newComment) {

        Comment comment = new Comment();

        comment.setContent(newComment.getContent());
        comment.setUserId(newComment.getUserId());
        comment.setArticleId(newComment.getArticleId());
        comment.setVoteDownCount(0L);
        comment.setVoteUpCount(0L);
        comment.setCreateAt(new Date());
        comment.setModifyAt(new Date());
        comment.setArticleId(newComment.getArticleId());

        comment.setParentId(comment.getParentId());

        return comment;

    }
}
