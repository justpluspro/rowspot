package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findCategoriesByParentId(Integer parentId);

    Optional<Category> findByName(String name);
}
