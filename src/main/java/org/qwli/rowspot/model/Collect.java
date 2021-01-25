package org.qwli.rowspot.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.qwli.rowspot.model.enums.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "collects")
@Entity(name = "Collect")
public class Collect extends BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;


    @Column(name = "category_id")
    private Long categoryId;


    @Column(name = "user_id")
    private Long userId;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at")
    @CreationTimestamp
    private Date createAt;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_at")
    @UpdateTimestamp
    private Date modifyAt;


    public Long getId() {
        return id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }
}
