package org.qwli.rowspot.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.qwli.rowspot.model.enums.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 分类
 * @author qwli7
 */
@Entity(name = "Category")
@Table(name = "categories",
        indexes = {
                @Index(name = "idx_categories_name", columnList = "name"),
                @Index(name = "idx_categories_parent_id", columnList = "parent_id")})
public class Category extends BaseEntity implements Serializable {


    /**
     * 分类id
     */
    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;

    /**
     * 分类名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 分类别名
     */
    @Column(name = "alias")
    private String alias;

    /**
     * 分类描述
     */
    @Column(name = "description", length = 100)
    private String description;

    /**
     * parent category
     */
    @Column(name = "parent_id")
    @ColumnDefault("0")
    private Long parentId;

    /**
     * check sort
     */
    @Column(name = "sort")
    @ColumnDefault("0")
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
