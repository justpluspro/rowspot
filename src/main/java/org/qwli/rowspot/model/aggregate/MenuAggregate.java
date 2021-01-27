package org.qwli.rowspot.model.aggregate;

import org.qwli.rowspot.model.Category;

import java.io.Serializable;

/**
 * @author qwli7
 */
public class MenuAggregate implements Serializable {

    private Long menuId;

    private String name;

    /**
     * 排序
     */
    private Integer sort = 0;


    public MenuAggregate(Category category) {
        this.menuId = category.getId();
        this.name = category.getName();
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSort() {
        return sort;
    }
}
