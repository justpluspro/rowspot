package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Collect;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends CrudRepository<Collect, Long>, QueryByExampleExecutor<Collect> {



}
