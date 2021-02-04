package org.qwli.rowspot.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.qwli.rowspot.model.enums.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tags")
@Entity(name = "Tag")
public class Tag extends BaseEntity implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.qwli.rowspot.model.IdGenerator")
    @GeneratedValue(generator = "idGenerator")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @Column(name = "alias", length = 23, nullable = false, unique = true)
    private String alias;

    @Column(name = "description", length = 1023)
    private String description;

    @Column(name = "icon", length = 256)
    private String icon;


    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createAt;


    @Column(name = "modify_at")
    @Temporal(TemporalType.DATE)
    @UpdateTimestamp
    private Date modifyAt;


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
