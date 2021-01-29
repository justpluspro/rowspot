package org.qwli.rowspot.service;

import org.qwli.rowspot.Message;
import org.qwli.rowspot.exception.BizException;
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

/**
 * @author qwli
 * 收藏 collect
 **/
@Service
public class CollectService extends AbstractService<Collect, Collect> {

    private final CollectRepository collectRepository;

    private final CategoryRepository categoryRepository;

    public CollectService(CollectRepository collectRepository, CategoryRepository categoryRepository) {
        this.collectRepository = collectRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * 保存收藏
     * @param collect collect
     * @throws BizException BizException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void save(Collect collect) throws BizException {
        Long id = collect.getCategoryId();
        categoryRepository.findById(id).orElseThrow(() -> new BizException(new Message("invalid category", "分类不存在")));
        collectRepository.save(collect);
    }

    /**
     * 删除收藏
     * @param id id
     * @param userId userId
     * @throws BizException BizException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void delete(Long id, Long userId) throws BizException {
         Collect prob = new Collect();
         prob.setUserId(userId);
         prob.setId(id);
         Example<Collect> example = Example.of(prob);
        
        Optional<Collect> collectOptional = collectRepository.findOne(example);
        collectOptional.ifPresent(collect -> collectRepository.deleteById(collectOptional.get().getId()));

    }


    /**
     * 分页查询收藏
     * @param page page
     * @param id id
     * @return PageAggregate
     * @throws BizException BizException
     */
    @Transactional(readOnly = true)
    public PageAggregate<CollectAggregate> findPage(Integer page, long id) throws BizException {
        if(page == null || page < 1) {
            page = 1;
        }

        final Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, PropertyName.createAt));

        final PageRequest pageRequest = PageRequest.of(page-1, 10, sort);

        Collect prob = new Collect();
        prob.setUserId(id);
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
