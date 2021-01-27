package org.qwli.rowspot.service;

import java.io.Serializable;

/**
 * @author qwli7
 * 新分类
 */
public class NewCategory implements Serializable {

    /**
     *  分类名称
     */
    private String name;

    /**
     * 分类别名
     */
    private String alias;

    /**
     * 上一级分类 id
     */
    private Long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
