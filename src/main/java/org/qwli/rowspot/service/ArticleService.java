package org.qwli.rowspot.service;


import org.qwli.rowspot.event.PersonNewsCreatedEvent;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.PropertyName;
import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.aggregate.ArticleAggregate;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.model.enums.ArticleState;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.factory.ArticleFactory;
import org.qwli.rowspot.model.Category;
import org.qwli.rowspot.repository.ArticleRepository;
import org.qwli.rowspot.repository.CategoryRepository;
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

import javax.persistence.criteria.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService extends AbstractService<Article, Article> {

    private ApplicationEventPublisher applicationEventPublisher;

    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;

    private final MarkdownParser markdownParser;

    public ArticleService(ArticleRepository articleRepository, CategoryRepository categoryRepository,
                          MarkdownParser markdownParser) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.markdownParser = markdownParser;
    }



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void save(NewArticle newArticle) throws BizException {
        final Category category = categoryRepository.findById(Long.valueOf(newArticle.getCategoryId())).orElseThrow(()
                -> new RuntimeException("illegal category"));
        final Article article = ArticleFactory.createNewArticle(newArticle, category);
        articleRepository.save(article);

        //发布个人动态创建事件
        PersonNewsCreatedEvent personNewsCreatedEvent = new PersonNewsCreatedEvent(this, article, new User(article.getUserId()));
        applicationEventPublisher.publishEvent(personNewsCreatedEvent);
    }

    @Transactional(readOnly = true)
    public PageAggregate<ArticleAggregate> findPage(ArticleQueryParam queryParam) throws RuntimeException {
        final String userId = queryParam.getUserId();
        long uid;
        try {
            uid = Long.parseLong(userId);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("invalid userId");
        }

        Integer page = queryParam.getPage();

        if(page == null || page < 1) {
            page = 1;
        }


        List<ArticleState> states = new ArrayList<>();
        states.add(ArticleState.POSTED);

        queryParam.setStates(states);


        final PageRequest pageRequest = PageRequest.of(page-1, 10,
                Sort.by(new Sort.Order(Sort.Direction.DESC, PropertyName.postAt)));


        Article prob = new Article();
        prob.setState(ArticleState.POSTED);
        prob.setUserId(uid);
        prob.setArticleType(queryParam.getArticleType());

        Example<Article> example = Example.of(prob);


//
//        Specification<Article> specification = (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            predicates.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), uid));
//            predicates.add(criteriaBuilder.equal(root.get("articleType").as(ArticleType.class), queryParam.getArticleType()));
//            predicates.add(criteriaBuilder.equal(root.get("state").as(ArticleState.class), ArticleState.POSTED));
//
//            Predicate[] pre = new Predicate[predicates.size()];
//            pre = predicates.toArray(pre);
//            return query.where(pre).getRestriction();
//        };

        final Page<Article> pageData = articleRepository.findAll(example, pageRequest);



        List<ArticleAggregate> articleAggregates = new ArrayList<>();
        for(int i = 0; i < pageData.getContent().size(); i++) {

            final Article article = pageData.getContent().get(i);
            ArticleAggregate articleAggregate = new ArticleAggregate(article, markdownParser, false);

            articleAggregates.add(articleAggregate);
        }

        return new PageAggregate<>(articleAggregates, pageData.getNumber() + 1, 10, pageData.getTotalPages());
    }

    @Transactional(readOnly = true)
    public ArticleAggregate findById(String id) {
        long aid;
        try{
            aid = Long.parseLong(id);
        } catch (NumberFormatException ex){
            throw new RuntimeException("invalid aid");
        }
        final Article article = articleRepository.findById(aid).orElseThrow(()
                -> new RuntimeException(""));
        ArticleAggregate articleAggregate = new ArticleAggregate(article, markdownParser, true);

        final ArticleType articleType = article.getArticleType();



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
