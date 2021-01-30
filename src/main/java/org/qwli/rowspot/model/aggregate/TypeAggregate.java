package org.qwli.rowspot.model.aggregate;

import java.io.Serializable;

/**
 * 类型聚合
 * @author liqiwen
 * @since 1.2
 */
public class TypeAggregate implements Serializable {

    private String name;

    private String alias;

    private boolean checked;

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}