package org.qwli.rowspot.model;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/3 8:17
 * 功能：SavedCategory
 **/
public class SavedCategory implements Serializable {

    private Long id;

    private String name;


    public SavedCategory(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
