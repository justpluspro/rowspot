package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Category;
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

    List<Category> findCategoriesByParentId(Integer parentId);

    Optional<Category> findByName(String name);
}
