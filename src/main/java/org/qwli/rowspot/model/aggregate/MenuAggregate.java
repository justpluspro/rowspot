package org.qwli.rowspot.model.aggregate;

import org.qwli.rowspot.model.Category;

import java.io.Serializable;

public class MenuAggregate implements Serializable {
    private Integer menuId;

    private String name;

    public MenuAggregate(Category category) {
        this.menuId = Math.toIntExact(category.getId());
        this.name = category.getName();
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
