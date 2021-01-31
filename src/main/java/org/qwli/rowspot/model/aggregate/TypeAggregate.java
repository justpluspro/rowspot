package org.qwli.rowspot.model.aggregate;

import java.io.Serializable;

/**
 * 类型聚合
 * @author liqiwen
 * @since 1.2
 */
public class TypeAggregate implements Serializable {

    /**
     * 类型名称
     */
    private String name;

    /**
     * 类型别名
     */
    private String alias;

    private long count = 0L;

    /**
     * 是否选中
     */
    private boolean checked;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}