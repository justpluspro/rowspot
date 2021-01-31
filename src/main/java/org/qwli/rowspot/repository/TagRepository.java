package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long>, QueryByExampleExecutor<Tag> {

}
