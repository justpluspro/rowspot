package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.PersonNews;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonNewsRepository extends CrudRepository<PersonNews, Long> {

}
