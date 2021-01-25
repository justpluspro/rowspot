package org.qwli.rowspot.model.factory;

import org.qwli.rowspot.service.NewArticle;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.Category;

public class ArticleFactory {

    public static Article createNewArticle(NewArticle newArticle, Category category) throws BizException {

        if(newArticle == null) {
            throw new BizException(null);
        }

//        final String articleType = newArticle.getArticleType();

        return new Article(newArticle, category);

    }
}
