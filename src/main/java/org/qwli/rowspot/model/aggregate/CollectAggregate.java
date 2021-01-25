package org.qwli.rowspot.model.aggregate;

import org.qwli.rowspot.model.enums.BaseEntity;
import org.qwli.rowspot.model.Collect;

import java.io.Serializable;
import java.util.Date;

public class CollectAggregate extends BaseEntity implements Serializable {

    private Long id;

    private Long categoryId;

    private String categoryName;

    private String categoryAlias;

    private Date createAt;

    public CollectAggregate(Collect collect) {
        this.id = collect.getId();
        this.categoryId = collect.getCategoryId();
        this.createAt = collect.getCreateAt();
    }


    public Long getId() {
        return id;
    }

    public String getCategoryAlias() {
        return categoryAlias;
    }

    public void setCategoryAlias(String categoryAlias) {
        this.categoryAlias = categoryAlias;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
