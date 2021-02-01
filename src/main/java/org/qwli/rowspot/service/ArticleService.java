package org.qwli.rowspot.service;


import org.qwli.rowspot.Message;
import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.event.ActivityCreatedEvent;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.exception.ResourceNotFoundException;
import org.qwli.rowspot.model.*;
import org.qwli.rowspot.model.aggregate.ArticleAggregate;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.model.aggregate.TypeAggregate;
import org.qwli.rowspot.model.enums.ArticleState;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.model.factory.ArticleFactory;
import org.qwli.rowspot.repository.ArticleRepository;
import org.qwli.rowspot.repository.ArticleTagRepository;
import org.qwli.rowspot.repository.CategoryRepository;
import org.qwli.rowspot.repository.CommentRepository;
import org.qwli.rowspot.service.processor.MarkdownProcessor;
import org.qwli.rowspot.web.ArticleQueryParam;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;;
import java.util.List;
import java.util.stream.Collectors;

/**
 * article Service
 * @author liqiwen
 * @since 1.2
 */
@Service
public class ArticleService extends AbstractService<Article, Article> {

    private ApplicationEventPublisher applicationEventPublisher;

    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;

    private final ArticleTagRepository articleTagRepository;

    private final CommentRepository commentRepository;

    private final MarkdownProcessor markdownProcessor;

    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository,
                          ArticleTagRepository articleTagRepository, CommentRepository commentRepository, MarkdownProcessor markdownProcessor) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.markdownProcessor = markdownProcessor;
        this.articleTagRepository = articleTagRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * saved article
     * @param newArticle newArticle
     * @return SavedArticle
     * @throws BizException
     * - category not exists
     * - menu not exists
     * - index unique exists
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public SavedArticle save(NewArticle newArticle) throws BizException {
        //check category
        final Category category = categoryRepository.findById(newArticle.getCategoryId()).orElseThrow(()
                -> new BizException(MessageEnum.CATEGORY_NOT_EXISTS));
        //create new article
        final Article article = ArticleFactory.createNewArticle(newArticle, category);
        final ArticleType articleType = article.getArticleType();
        if (ArticleType.A.equals(articleType)) {
            Category probe = new Category();
            probe.setParentId(category.getId());
            probe.setId(article.getMenuId());
            Example<Category> example = Example.of(probe);
            //check menu if article type is 'A'
            categoryRepository.findOne(example).orElseThrow(() ->
                    new BizException(MessageEnum.MENU_NOT_EXISTS));
        }
        //check index unique article if article type is 'I'
        checkIndexUnique(articleType, category);


        //save article
        articleRepository.save(article);

        //publish create article event
        ActivityCreatedEvent activityCreatedEvent = new ActivityCreatedEvent(this, article, new User(article.getUserId()));
        applicationEventPublisher.publishEvent(activityCreatedEvent);

        //return saved article for caller
        SavedArticle savedArticle = new SavedArticle();
        savedArticle.setId(article.getId());
        savedArticle.setState(ArticleState.POSTED);
        savedArticle.setChecking(true);
        return savedArticle;
    }

    /**
     * check article is unique if article type is 'I'
     * @param articleType articleType
     * @param category category
     */
    private synchronized void checkIndexUnique(ArticleType articleType, Category category) throws BizException {
        if(ArticleType.I.equals(articleType)) {
            Article probe = new Article();
            probe.setIndexUnique(true);
            probe.setState(ArticleState.POSTED);
            probe.setCategoryId(category.getId());
            Example<Article> example = Example.of(probe);

            articleRepository.findOne(example).ifPresent(e ->  {
                throw new BizException(MessageEnum.ARTICLE_INDEX_UNIQUE);
            });
        }
    }

    /**
     * changed visits
     * @param id id
     * @throws BizException BizException
     * maybe throws some exception
     * - resource not found
     * - article state is not posted
     * - if article's author is current logged user, don't change
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void hit(Long id) throws BizException {
        //check article exists
        final Article article = articleRepository.findById(id).orElseThrow(()
                -> new BizException(MessageEnum.RESOURCE_NOT_FOUND));

        //check state
        if(article.getState() != ArticleState.POSTED) {
            throw new BizException(MessageEnum.ARTICLE_STATE_ERROR);
        }

        Long visits = article.getVisits();
        visits = visits + 1;
        article.setVisits(visits);
    }

    /**
     * search article for page
     * @param queryParam queryParam
     * @return PageAggregate
     * @throws ResourceNotFoundException exception
     */
    @Transactional(readOnly = true)
    public PageAggregate<ArticleAggregate> findPage(ArticleQueryParam queryParam) throws ResourceNotFoundException {
        final Long userId = queryParam.getUserId();
        Article probe = new Article();
        if(userId != null) {
            probe.setUserId(userId);
        }
        Long categoryId = queryParam.getCategoryId();
        if(categoryId != null) {
            probe.setCategoryId(categoryId);
        }
        ArticleType articleType = queryParam.getArticleType();
        if(articleType != null) {
            probe.setArticleType(articleType);
        }

        ArticleState articleState = queryParam.getArticleState();
        if(articleState != null) {
            probe.setState(articleState);
        }

        final PageRequest pageRequest = PageRequest.of(queryParam.getPage()-1, queryParam.getSize(),
                Sort.by(new Sort.Order(Sort.Direction.DESC, PropertyName.postAt)));

        Example<Article> example = Example.of(probe);

        final Page<Article> pageData = articleRepository.findAll(example, pageRequest);

        List<ArticleAggregate> articleAggregates = pageData.stream().map(e -> new ArticleAggregate(e, markdownProcessor, false)).collect(Collectors.toList());

        return new PageAggregate<>(articleAggregates, pageData.getNumber() + 1, queryParam.getSize(), pageData.getTotalPages());
    }

    /**
     * find article by id
     * @param id id
     * @return article aggregate
     * @throws ResourceNotFoundException maybe resource not found exception
     */
    @Transactional(readOnly = true, rollbackFor = ResourceNotFoundException.class)
    public ArticleAggregate findById(Long id) throws ResourceNotFoundException {

        //check article
        final Article article = articleRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(MessageEnum.ARTICLE_NOT_EXISTS));

        //check state
        ArticleState state = article.getState();
        if(state != ArticleState.POSTED) {
            throw new ResourceNotFoundException(MessageEnum.ARTICLE_STATE_ERROR);
        }

        //build article aggregate
        ArticleAggregate articleAggregate = new ArticleAggregate(article, markdownProcessor, true);

        //check category
        final Category category = categoryRepository.findById(article.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageEnum.CATEGORY_NOT_EXISTS));

        //assemble type
        final List<TypeAggregate> typeAggregates = ArticleType.findAll(category, article.getArticleType());

        articleAggregate.setTypeAggregates(typeAggregates);

        return articleAggregate;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void update(Article article) throws BizException {
        final Article existsArticle = articleRepository.findById(article.getId()).orElseThrow(()
                -> new BizException(MessageEnum.ARTICLE_NOT_EXISTS));

        //  update article
//        final Long categoryId = article.getCategoryId();

//        categoryRepository.fin

    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void delete(Long id) throws BizException {
        final Article article = articleRepository.findById(id).orElseThrow(()
                -> new BizException(MessageEnum.ARTICLE_NOT_EXISTS));

        // delete article
        articleRepository.deleteById(article.getId());


        // delete articleTags
        ArticleTag probe = new ArticleTag();
        probe.setArticleId(article.getId());
        Example<ArticleTag> example = Example.of(probe);

        final List<ArticleTag> articleTags = articleTagRepository.findAll(example);
        articleTagRepository.deleteInBatch(articleTags);


        // delete comment
        Comment comment = new Comment();
        comment.setArticleId(article.getId());
        commentRepository.deleteAll(commentRepository.findAll(Example.of(comment)));

    }


    @Transactional(readOnly = true, rollbackFor = ResourceNotFoundException.class)
    public ArticleAggregate findIndexUnique(String alias) {
        final Category category = categoryRepository.findByName(alias).orElseThrow(()
                -> new ResourceNotFoundException(MessageEnum.CATEGORY_NOT_EXISTS));

        Article probe = new Article();
        probe.setIndexUnique(true);
        probe.setCategoryId(category.getId());
        probe.setArticleType(ArticleType.A);
        probe.setState(ArticleState.POSTED);


        Example<Article> example = Example.of(probe);
        final Article article = articleRepository.findOne(example).orElseThrow(()
                -> new ResourceNotFoundException(MessageEnum.ARTICLE_NOT_EXISTS));
        ArticleAggregate articleAggregate = new ArticleAggregate(article);

        final List<TypeAggregate> typeAggregates = ArticleType.findAll(category, ArticleType.A);

        articleAggregate.setTypeAggregates(typeAggregates);

        return articleAggregate;
    }

    public List<Article> findLatestNews(ArticleType articleType) {

        Article prob = new Article();
        prob.setArticleType(articleType);
        prob.setState(ArticleState.POSTED);
        Example<Article> example = Example.of(prob);

        final PageRequest pageRequest = PageRequest.of(0, 10,
                Sort.by(new Sort.Order(Sort.Direction.DESC, PropertyName.postAt)));

        final Page<Article> page = articleRepository.findAll(example, pageRequest);

        return page.getContent();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }



    static class ArticleSpec {


        public static Specification<Article> isStateIn(List<ArticleState> states) {
            return (root, query, builder) -> {
                return builder.in(root.get("state"));
            };
        }


        public static Specification<Article> isArticleType(ArticleType articleType) {
            return (root, query, builder) -> builder.equal(root.get("articleType"), articleType);
        }


        public static Specification<Article> isUserId(Path<String> userId) {
            return (root, query, builder) -> builder.equal(root.get("userId"), userId);
        }
    }
}
