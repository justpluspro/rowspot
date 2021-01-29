package org.qwli.rowspot.service;


import org.qwli.rowspot.Message;
import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.event.ActivityCreatedEvent;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.exception.ResourceNotFoundException;
import org.qwli.rowspot.model.*;
import org.qwli.rowspot.model.aggregate.ArticleAggregate;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.model.enums.ArticleState;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.model.factory.ArticleFactory;
import org.qwli.rowspot.repository.ArticleRepository;
import org.qwli.rowspot.repository.CategoryRepository;
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
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章 Service
 * @author liqiwen
 * @since 1.2
 */
@Service
public class ArticleService extends AbstractService<Article, Article> {

    private ApplicationEventPublisher applicationEventPublisher;

    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;

    private final MarkdownProcessor markdownProcessor;

    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository,
                          MarkdownProcessor markdownProcessor) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.markdownProcessor = markdownProcessor;
    }

    /**
     * 保存文章
     * @param newArticle newArticle
     * @return SavedArticle
     * @throws BizException 业务异常
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public SavedArticle save(NewArticle newArticle) throws BizException {
        final Category category = categoryRepository.findById(newArticle.getCategoryId()).orElseThrow(()
                -> new BizException(new Message("category.notExists", "分类不存在")));
        final Article article = ArticleFactory.createNewArticle(newArticle, category);
        final ArticleType articleType = article.getArticleType();
        if (ArticleType.A.equals(articleType)) {
            Category probe = new Category();
            probe.setParentId(category.getId());
            probe.setId(article.getMenuId());
            Example<Category> example = Example.of(probe);
            categoryRepository.findOne(example).orElseThrow(() ->
                    new BizException(new Message("menu.notExists", "菜单不存在")));
        }
        checkIndexUnique(articleType, category);

        articleRepository.save(article);

        //发布个人动态创建事件
        ActivityCreatedEvent activityCreatedEvent = new ActivityCreatedEvent(this, article, new User(article.getUserId()));
        applicationEventPublisher.publishEvent(activityCreatedEvent);

        SavedArticle savedArticle = new SavedArticle();
        savedArticle.setId(article.getId());
        savedArticle.setState(ArticleState.POSTED);
        savedArticle.setChecking(true);
        return savedArticle;
    }

    /**
     * 检查文章是否是当前分类下的唯一文章
     * @param articleType articleType
     * @param category category
     */
    private synchronized void checkIndexUnique(ArticleType articleType, Category category) {
        if(ArticleType.I.equals(articleType)) {
            Article probe = new Article();
            probe.setIndexUnique(true);
            probe.setCategoryId(category.getId());
            Example<Article> example = Example.of(probe);

            articleRepository.findOne(example).ifPresent(e ->  {
                throw new BizException(new Message("indexUnique.exists", "已经存在"));
            });
        }
    }

    /**
     * 点击量变化
     * @param id id
     * @throws BizException BizException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void hit(Long id) throws BizException {
        final Article article = articleRepository.findById(id).orElseThrow(()
                -> new BizException(MessageEnum.RESOURCE_NOT_FOUND));

        Long visits = article.getVisits();
        visits = visits + 1;
        article.setVisits(visits);
    }

    /**
     * 分页查询文章
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

    @Transactional(readOnly = true, rollbackFor = ResourceNotFoundException.class)
    public ArticleAggregate findById(String id) throws ResourceNotFoundException {
        long aid;
        try{
            aid = Long.parseLong(id);
        } catch (NumberFormatException ex){
            throw new ResourceNotFoundException("invalid aid");
        }
        final Article article = articleRepository.findById(aid).orElseThrow(()
                -> new ResourceNotFoundException(""));
        
        
        ArticleState state = article.getState();
        //非发布状态不允许查看 && 非自己的内容 && 未登录
//        if(state != ArticleState.POSTED && article.getUserId() != userId && !EnvironmentContext.isAuthenticated()) {
//            throw new ResourceNotFoundException("invalid access state");
//        }
        
       
        final ArticleType articleType = article.getArticleType();
        // 判断当前文章应该选中哪个页签
//        for(ArticleType type : ArticleType.findAll()) {
//            if(type == ArticleType.A || type == ArticleType.I) {
//
//            }
//        }
        
        ArticleAggregate articleAggregate = new ArticleAggregate(article, markdownProcessor, true);

      
        return articleAggregate;
    }

    public ArticleAggregate findIndexUnique(String categoryName) {
        final Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new RuntimeException(""));
//        articleRepositoryJpa.findByIndexUniqueAndCategoryId(true, Math.toIntExact(category.getId()));


        return null;
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
