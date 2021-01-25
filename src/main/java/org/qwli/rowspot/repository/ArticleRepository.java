package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.enums.ArticleState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long>, JpaSpecificationExecutor<Article>, QueryByExampleExecutor<Article> {


    List<Article> findArticlesByUserIdAndStateIn(Integer userId, List<ArticleState> states, Pageable pageable);

    int countArticlesByUserIdAndStateInAndArticleType(Integer userId, List<ArticleState> states, ArticleType articleType);


//    Optional<ArticleAggregate> findByIndexUniqueAndCategoryId(Boolean indexUnique, Integer categoryId);

//    void findIndexUnique(Long id, boolean b);
}
