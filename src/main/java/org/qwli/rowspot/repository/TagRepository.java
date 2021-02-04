package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author qwli7
 */
@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long>, QueryByExampleExecutor<Tag> {

    /**
     * find tag by name
     * @param name name
     * @return Tag
     */
    Optional<Tag> findByName(String name);

    /**
     * find tag by alias
     * @param alias alias
     * @return Tag
     */
    Optional<Tag> findByAlias(String alias);

}
