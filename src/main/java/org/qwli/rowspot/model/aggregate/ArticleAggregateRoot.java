package org.qwli.rowspot.model.aggregate;

import org.qwli.rowspot.model.Article;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArticleAggregateRoot implements Serializable {


    private List<ArticleAggregate> aggregates;


    public ArticleAggregateRoot(List<Article> articles) {
        if(CollectionUtils.isEmpty(articles)) {
            aggregates = new ArrayList<>();
        } else {
            aggregates = new ArrayList<>(articles.size());
            for(Article article : articles) {
                ArticleAggregate aggregate = new ArticleAggregate(article, null, false);
                aggregates.add(aggregate);
            }
        }
    }

    public List<ArticleAggregate> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<ArticleAggregate> aggregates) {
        this.aggregates = aggregates;
    }
}
