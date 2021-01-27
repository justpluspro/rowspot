package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

/**
 * 评论 Repository
 * @author liqiwen
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>, QueryByExampleExecutor<Comment> {

}
