package org.qwli.rowspot.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.qwli.rowspot.model.enums.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Category")
@Table(name = "categories",
        indexes = {
                @Index(name = "idx_categories_name", columnList = "name"),
                @Index(name = "idx_categories_parent_id", columnList = "parent_id")})
public class Category extends BaseEntity implements Serializable {


    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "alias")
    private String alias;


    @Column(name = "description", length = 100)
    private String description;

    /**
     * parent category
     */
    @Column(name = "parent_id")
    @ColumnDefault("0")
    private Integer parentId;


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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
