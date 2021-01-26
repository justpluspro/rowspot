package org.qwli.rowspot.service;

import org.qwli.rowspot.model.PersonNews;
import org.qwli.rowspot.repository.PersonNewsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * @author liqiwen
 * 个人动态实现
**/
@Service
public class PersonNewsService extends AbstractService<PersonNews, PersonNews> {


    private final PersonNewsRepository personNewsRepository;

    public PersonNewsService(PersonNewsRepository personNewsRepository) {
        this.personNewsRepository = personNewsRepository;
    }
    
    
    public List<PersonNews> findPage(Integer page, Long userId) {
        if(page == null || page <= 0) {
            page = 1;
        }
        
        //初始化默认 size
        int defaultSize = 10;
        
        Sort sort = Sort.of(Order.by(PropertyName.createAt, Order.DESC));
        PageRequest pageRequest = PageRequest.of(page-1, defaultSize, sort);
        
        PersonNews prob = new PersonNews();
        prob.setUserId(userId);
        
        Example<PersonNews> example = Example.of(prob);
        
        Page<PersonNews> pageData = personNewsRepository.find(example, pageRequest);
        
        
        
        return pageData;
    }

    public List<PersonNews> recentPersonNews(Integer userId) {

//        personNewsRepositoryJpa.find

        return new ArrayList<>();

    }








}
