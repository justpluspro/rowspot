package org.qwli.rowspot.model.factory;

import org.qwli.rowspot.model.Comment;

/**
 * @author qwli7
 * @date 2021/1/27 13:36
 * 功能：CommentFactory
 **/
public class CommentFactory {

    public static Comment createNewComment(NewComment newComment) {

        Comment comment = new Comment();


        return comment;

    }
}
