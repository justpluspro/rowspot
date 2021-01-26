package org.qwli.rowspot.service;

import org.qwli.rowspot.model.Activity;
import org.qwli.rowspot.model.PropertyName;
import org.qwli.rowspot.model.aggregate.ActivityAggregate;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.repository.ActivitiesRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
/**
 * @author liqiwen
 * 个人动态实现
 * @since 1.0
**/
@Service
public class ActivityService extends AbstractService<Activity, Activity> {


    private final ActivitiesRepository activitiesRepository;

    public ActivityService(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }
    

    @Transactional(readOnly = true)
    public PageAggregate<ActivityAggregate> findPage(Integer page, Long userId) {
        if(page == null || page <= 0) {
            page = 1;
        }
        
        //初始化默认 size
        int defaultSize = 10;
        
        Sort sort = Sort.by(Sort.Order.by(PropertyName.createAt));
        PageRequest pageRequest = PageRequest.of(page-1, defaultSize, sort);
        
        Activity prob = new Activity();
        prob.setUserId(userId);
        
        Example<Activity> example = Example.of(prob);
        
        Page<Activity> pageData = activitiesRepository.findAll(example, pageRequest);
        
        
        
        return new PageAggregate<>(new ArrayList<>(), 0, 0);
    }

    public List<Activity> recentPersonNews(Integer userId) {

//        personNewsRepositoryJpa.find

        return new ArrayList<>();

    }
}
