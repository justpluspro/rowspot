package org.qwli.rowspot.model.factory;

import org.qwli.rowspot.model.enums.ArticleState;
import org.qwli.rowspot.model.enums.ArticleType;
import org.qwli.rowspot.model.enums.EditorType;
import org.qwli.rowspot.service.NewArticle;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.Category;

import java.util.Date;

/**
 * 文章工厂
 * @author liqiwen
 * @since 1.2
 */
public class ArticleFactory {

    public static Article createNewArticle(NewArticle newArticle, Category category) throws BizException {

        final Article article = new Article();
        article.setUserId(newArticle.getUserId());
        article.setTitle(newArticle.getTitle());
        article.setContent(newArticle.getContent());
        article.setCreateAt(new Date());
        article.setModifyAt(new Date());

        article.setAllowComment(newArticle.isAllowComment());
        article.setComments(0L);
        article.setLikes(0L);
        article.setVisits(0L);
        article.setVotes(0L);


        article.setEditorType(EditorType.MD);
        article.setCategoryId(category.getId());

        final String articleType = newArticle.getArticleType();

         for(ArticleType value: ArticleType.values()) {
             final String typeName = value.name();
             if(typeName.equals(articleType)) {
                 article.setArticleType(value);
                 article.setIndexUnique(ArticleType.isIndexUnique(typeName));
                 break;
            }
        }
         if(article.getArticleType() == null) {
             article.setArticleType(ArticleType.A);
             article.setMenuId(newArticle.getMenuId());
         }

         article.setSolved(false);
         article.setState(ArticleState.POSTED);
         article.setPostAt(new Date());
         article.setSummary("");

        return article;
    }
}
