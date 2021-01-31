package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

}
