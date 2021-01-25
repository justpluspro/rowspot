package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {



    Optional<User> findByEmail(String email);


    @Query("update User u set u.lastLoginAt = ?2 where u.id = ?1")
    @Modifying
    void updateUserLoginDate(@Param("id") Long id, @Param("lastLoginAt") Date date);


}
