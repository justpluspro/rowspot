package org.qwli.rowspot.model.factory;

import org.qwli.rowspot.service.NewCategory;
import org.qwli.rowspot.model.Category;

public class CategoryFactory {

    public static Category createCategory(NewCategory newCategory) {
        final Category category = new Category();
        final Integer parentId = newCategory.getParentId();
        if(parentId != null && parentId > 0) {
            category.setParentId(parentId);
        }

        category.setName(newCategory.getName());
        category.setAlias(newCategory.getAlias());

        return category;
    }
}
