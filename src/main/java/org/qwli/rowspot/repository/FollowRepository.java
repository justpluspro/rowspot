package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Follow;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepository extends PagingAndSortingRepository<Follow, Long>, QueryByExampleExecutor<Follow> {

    /**
     * 查询当前用户是否已经关注了某个用户
     * @param userId userId
     * @param followUserId followUserId
     * @return Follow
     */
    Optional<Follow> findByUserIdAndFollowUserId(Long userId, Long followUserId);
}
