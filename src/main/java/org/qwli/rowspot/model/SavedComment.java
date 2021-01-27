package org.qwli.rowspot.model;

import java.io.Serializable;

public class SavedComment implements Serializable {

    private Long id;

    private Boolean checking;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getChecking() {
        return checking;
    }

    public void setChecking(Boolean checking) {
        this.checking = checking;
    }
}
