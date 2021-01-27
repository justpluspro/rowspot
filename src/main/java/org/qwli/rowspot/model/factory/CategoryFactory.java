package org.qwli.rowspot.model.factory;

import org.qwli.rowspot.service.NewCategory;
import org.qwli.rowspot.model.Category;

/**
 * @author qwli7
 * 分类工厂
 */
public class CategoryFactory {

    /**
     * 创建一个 Category
     * @param newCategory newCategory
     * @return Category
     */
    public static Category createCategory(NewCategory newCategory) {
        final Category category = new Category();
        final Long parentId = newCategory.getParentId();
        if(parentId != null && parentId > 0) {
            category.setParentId(parentId);
        }
        category.setName(newCategory.getName());
        category.setAlias(newCategory.getAlias());
        return category;
    }
}
