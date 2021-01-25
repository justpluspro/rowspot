package org.qwli.rowspot.service;

import org.qwli.rowspot.model.PropertyName;
import org.qwli.rowspot.model.aggregate.CollectAggregate;
import org.qwli.rowspot.model.aggregate.PageAggregate;
import org.qwli.rowspot.model.Category;
import org.qwli.rowspot.model.Collect;
import org.qwli.rowspot.repository.CategoryRepository;
import org.qwli.rowspot.repository.CollectRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CollectService extends AbstractService<Object, Object> {

    private final CollectRepository collectRepository;

    private final CategoryRepository categoryRepository;

    public CollectService(CollectRepository collectRepository, CategoryRepository categoryRepository) {
        this.collectRepository = collectRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Collect collect) {
        collectRepository.save(collect);
    }


    @Transactional(readOnly = true)
    public PageAggregate<CollectAggregate> findPage(Integer page, String id) throws RuntimeException {
        if(page == null || page < 1) {
            page = 1;
        }

        final Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, PropertyName.createAt));

        final PageRequest pageRequest = PageRequest.of(page-1, 10, sort);

        Collect prob = new Collect();
        prob.setUserId(Long.parseLong(id));
        Example<Collect> example = Example.of(prob);
        final Page<Collect> pageData = collectRepository.findAll(example, pageRequest);




        List<CollectAggregate> collectAggregates = new ArrayList<>();
        for(int i = 0; i < pageData.getContent().size(); i++) {
            final Collect collect = pageData.getContent().get(i);
            final Optional<Category> byId = categoryRepository.findById(collect.getCategoryId());
            if(!byId.isPresent()) {
                continue;
            }

            final Category category = byId.get();
            if(category.getParentId() != 0) {
                continue;
            }

            CollectAggregate collectAggregate = new CollectAggregate(collect);
            collectAggregate.setCategoryName(category.getName());
            collectAggregate.setCategoryAlias(category.getAlias());
            collectAggregates.add(collectAggregate);
        }

        return new PageAggregate<>(collectAggregates,
                pageData.getNumber(), 10, pageData.getTotalPages());


    }
}
