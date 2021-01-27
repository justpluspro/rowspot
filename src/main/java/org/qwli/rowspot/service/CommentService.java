package org.qwli.rowspot.service;

import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.Comment;
import org.qwli.rowspot.model.SavedComment;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.aggregate.CommentAggregate;
import org.qwli.rowspot.model.enums.ArticleState;
import org.qwli.rowspot.model.factory.CommentFactory;
import org.qwli.rowspot.repository.ArticleRepository;
import org.qwli.rowspot.repository.CommentRepository;
import org.qwli.rowspot.repository.UserRepository;
import org.qwli.rowspot.service.processor.MarkdownProcessor;
import org.qwli.rowspot.web.CommentQueryParam;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.plugin.util.UserProfile;
import sun.plugin2.message.Message;

import java.util.*;

/**
 * CommentService
 * @author liqiwen
 */
@Service
public class CommentService extends AbstractService<Comment, Comment> {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MarkdownProcessor markdownProcessor;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository,
                          MarkdownProcessor markdownProcessor, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.markdownProcessor = markdownProcessor;
        this.userRepository = userRepository;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public SavedComment save(NewComment newComment) throws BizException {
        final Comment comment = CommentFactory.createNewComment(newComment);
        final Long articleId = comment.getArticleId();
        final Article article = articleRepository.findById(articleId).orElseThrow(() -> new BizException(MessageEnum.RESOURCE_NOT_FOUND));
        final ArticleState state = article.getState();
        if(state != ArticleState.POSTED) {
            //无效的状态评论
            throw new BizException(MessageEnum.RESOURCE_NOT_FOUND);
        }

        //父评论是否为空
        final Long parentId = comment.getParentId();
        if(parentId != null) {
            final Comment parent = commentRepository.findById(parentId).orElseThrow(()
                    -> new BizException(MessageEnum.RESOURCE_NOT_FOUND));
            if (parent.getChecking() == null || parent.getChecking()) {
                //父评论子在审核中
                throw new BizException(MessageEnum.RESOURCE_NOT_FOUND);
            }

        }

        comment.setChecking(false);

        commentRepository.save(comment);

        SavedComment savedComment = new SavedComment();
        savedComment.setChecking(false);
        savedComment.setId(comment.getId());

        return savedComment;
    }

    /**
     * 查询评论
     * @param commentQueryParam commentQueryParam
     * @return List
     */
    @Transactional(readOnly = true)
    public List<CommentAggregate> findPage(CommentQueryParam commentQueryParam) throws BizException {
        final Long articleId = commentQueryParam.getArticleId();
        articleRepository.findById(articleId).orElseThrow(() -> new BizException(MessageEnum.RESOURCE_NOT_FOUND));
        Comment probe = new Comment();
        probe.setArticleId(articleId);
        probe.setChecking(false);
        Example<Comment> example = Example.of(probe);

        PageRequest pageRequest = PageRequest.of(commentQueryParam.getPage()-1, commentQueryParam.getSize(),
                Sort.by(Sort.Order.desc("createAt")));

        final Page<Comment> page = commentRepository.findAll(example, pageRequest);

//        for(User)
        final List<Comment> content = page.getContent();
        List<CommentAggregate> commentAggregates = new ArrayList<>();
        for(Comment comment : content) {
            CommentAggregate commentAggregate = new CommentAggregate();
            commentAggregate.setContent(markdownProcessor.process(comment.getContent()));
            commentAggregate.setId(comment.getId());
            commentAggregate.setChecking(comment.getChecking());
            commentAggregate.setCreateAt("");
            final Optional<User> userOp = userRepository.findById(comment.getUserId());
            if(!userOp.isPresent()){
                continue;
            }
            Map<String, String> user = new HashMap<>();
            user.put("id", userOp.get().getId().toString());
            user.put("nickname", userOp.get().getEmail());
            commentAggregate.setUser(user);

            Map<String, String> articleMap = new HashMap<>();
            articleMap.put("id", articleId.toString());

            commentAggregate.setArticle(articleMap);

            commentAggregates.add(commentAggregate);
        }

        return commentAggregates;
    }
}
