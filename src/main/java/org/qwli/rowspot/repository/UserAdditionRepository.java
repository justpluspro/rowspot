package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.UserAddition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAdditionRepository extends CrudRepository<UserAddition, Long> {

    Optional<UserAddition> findUserAdditionByUserId(Long id);
}
