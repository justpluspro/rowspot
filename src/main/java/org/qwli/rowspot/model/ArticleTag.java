package org.qwli.rowspot.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "ArticleTag")
@Table(name = "article_tags", indexes = {
        @Index(name = "idx_article_tags_article_id", columnList = "article_id"),
        @Index(name = "idx_article_tags_tag_id", columnList = "tag_id")
})
public class ArticleTag implements Serializable {


    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Integer id;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
