package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.User;
import org.qwli.rowspot.model.aggregate.UserAggregateRoot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

/**
 * @author qwli7
 * User Repository
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>, QueryByExampleExecutor<User> {

    @Query("update User u set u.lastLoginAt = ?2 where u.id = ?1")
    @Modifying
    void updateUserLoginDate(@Param("id") Long id, @Param("lastLoginAt") Date date);
//
//    /**
//     * 根据邮件查询用户，这里只查询两个字段  email 和 password
//     * @param email email
//     * @return User
//     */
//    @Query(value = "select u.email, u.password from User u where u.email = ?1")
//    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findUserByEmail(String email);
}
