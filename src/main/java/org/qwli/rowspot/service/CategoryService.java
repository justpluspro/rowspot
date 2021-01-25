package org.qwli.rowspot.service;

import org.qwli.rowspot.model.aggregate.MenuAggregate;
import org.qwli.rowspot.model.Category;
import org.qwli.rowspot.model.factory.CategoryFactory;
import org.qwli.rowspot.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService extends AbstractService<Category, Category> {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> findAll() {
        return categoryRepository.findCategoriesByParentId(0);
    }

    public List<MenuAggregate> findMenuById(int id) {

        final List<Category> categoriesByParentId = categoryRepository.findCategoriesByParentId(id);

        return categoriesByParentId.stream().map(MenuAggregate::new).collect(Collectors.toList());

    }

    public void save(NewCategory newCategory) {
        final Category category = CategoryFactory.createCategory(newCategory);
        categoryRepository.save(category);
    }
}
