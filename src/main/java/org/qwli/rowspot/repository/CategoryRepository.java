package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * CategoryRepository
 * @author liqiwen
 * @since 1.0
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>, QueryByExampleExecutor<Category> {
    
    Optional<Category> findByName(String name);

    /**
     * get max sort from parentId
     * @param parentId parentId
     * @return Integer
     */
    @Query("select max(sort)+1 from Category where parentId = ?1")
    Integer findMaxSort(Long parentId);
}
