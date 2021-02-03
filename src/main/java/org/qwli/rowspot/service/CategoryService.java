package org.qwli.rowspot.service;

import org.qwli.rowspot.Message;
import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.exception.BizException;
import org.qwli.rowspot.model.Article;
import org.qwli.rowspot.model.SavedCategory;
import org.qwli.rowspot.model.aggregate.MenuAggregate;
import org.qwli.rowspot.model.Category;
import org.qwli.rowspot.model.factory.CategoryFactory;
import org.qwli.rowspot.repository.ArticleRepository;
import org.qwli.rowspot.repository.CategoryRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qwli7
 */
@Service
public class CategoryService extends AbstractService<Category, Category> {

    private final CategoryRepository categoryRepository;

    private final ArticleRepository articleRepository;

    public CategoryService(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Category> findAll() {
        Category probe = new Category();
        probe.setParentId(0L);
        Example<Category> example = Example.of(probe);
        final Iterable<Category> categoryIterable = categoryRepository.findAll(example);
        final Iterator<Category> iterator = categoryIterable.iterator();

        List<Category> categories = new ArrayList<>();
        while (iterator.hasNext()){
            final Category next = iterator.next();
            categories.add(next);
        }
        return categories;
    }

    /**
     * 获取父分类下的菜单
     * @param parentId parentId
     * @return List
     */
    @Transactional(readOnly = true)
    public List<MenuAggregate> findMenuById(Long parentId) {
        Category probe = new Category();
        probe.setParentId(parentId);
        Example<Category> example = Example.of(probe);
        Iterable<Category> categories = categoryRepository.findAll(example);
        Iterator<Category> iterator = categories.iterator();
        List<MenuAggregate> menuAggregates = new ArrayList<>();
        while (iterator.hasNext()) {
            Category next = iterator.next();
            MenuAggregate menuAggregate = new MenuAggregate(next);
            menuAggregates.add(menuAggregate);
        }
        //按照 sort 进行排序
        return menuAggregates.stream().sorted(Comparator.comparingInt(MenuAggregate::getSort)).collect(Collectors.toList());
    }

    /**
     * 保存分类
     * 一级分类和二级分类的区别在于是否存在 parentId
     * @param newCategory newCategory
     * @throws BizException BizException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public SavedCategory save(NewCategory newCategory) throws BizException {
        final Category category = CategoryFactory.createCategory(newCategory);
        Long parentId = category.getParentId();
        if(parentId != null && parentId > 0) {
            categoryRepository.findById(parentId).orElseThrow(()
                    -> new BizException(new Message("parent.notExists", "父分类不存在")));

            Category probe = new Category();
            probe.setParentId(parentId);
            probe.setName(category.getName());
            Example<Category> example = Example.of(probe);

            categoryRepository.findOne(example).ifPresent(e -> {
                throw new BizException(new Message("childName.exists", "该分类名称已经在"));
            });
        } else {
            Category probe = new Category();
            probe.setName(category.getName());
            Example<Category> example = Example.of(probe);
            categoryRepository.findOne(example).ifPresent(e -> {
                throw new BizException(new Message("parentName.exists", "名称已经存在"));
            });
        }
        categoryRepository.save(category);

        return new SavedCategory(category.getId(), category.getName());
    }

    @Transactional(readOnly = true)
    public Optional<Category> findOne(String categoryAlias) {
        if(categoryAlias.startsWith("/")) {
            categoryAlias = categoryAlias.substring(1);
        }
        Category probe = new Category();
        probe.setAlias(categoryAlias);
        probe.setParentId(0L);
        Example<Category> example = Example.of(probe);
        return categoryRepository.findOne(example);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BizException.class)
    public void delete(Long id) throws BizException {
        final Category category = categoryRepository.findById(id).orElseThrow(()
                -> new BizException(MessageEnum.RESOURCE_NOT_FOUND));

        final Long parentId = category.getParentId();
        Article probe = new Article();
        probe.setCategoryId(category.getParentId());
        if(parentId != null && parentId > 0) {
            probe.setMenuId(category.getId());
        }
        Example<Article> example = Example.of(probe);
        final Iterable<Article> articleIterable = articleRepository.findAll(example);
        for (Article next : articleIterable) {
            next.setMenuId(null);
        }
        categoryRepository.deleteById(category.getId());
    }
}
