package org.qwli.rowspot.repository;

import org.qwli.rowspot.model.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

/**
 * 活动
 * @author liqiwen
 * @since 1.0
 */
@Repository
public interface ActivitiesRepository extends CrudRepository<Activity, Long>, QueryByExampleExecutor<Activity> {

}
