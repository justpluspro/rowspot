package org.qwli.rowspot.service;

import org.qwli.rowspot.model.PersonNews;
import org.qwli.rowspot.repository.PersonNewsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonNewsService extends AbstractService<PersonNews, PersonNews> {


    private final PersonNewsRepository personNewsRepository;

    public PersonNewsService(PersonNewsRepository personNewsRepository) {
        this.personNewsRepository = personNewsRepository;
    }

    public List<PersonNews> recentPersonNews(Integer userId) {

//        personNewsRepositoryJpa.find


        return new ArrayList<>();

    }








}
